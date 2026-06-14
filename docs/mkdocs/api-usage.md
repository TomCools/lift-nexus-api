# API Usage

This page explains how to use the Lift Nexus API for the current static dispatching workflow.

!!! info "MVP scope"
    This documentation reflects the v0.1.0 static dispatching MVP. The API does not yet support authentication, real-time replanning, or production deployment. See [Known Limitations](limitations.md) for details.

---

## Prerequisites

Start the application and PostgreSQL database:

```bash
docker compose up --build
```

The API will be available at `http://localhost:8080`. Swagger UI provides interactive documentation at:

```
http://localhost:8080/swagger-ui.html
```

!!! tip "Explore Swagger UI"
    Swagger UI shows the full request and response schemas for every endpoint. Use it to inspect field names, data types, validation rules, and example payloads. This page focuses on the workflow — the exact schemas are best explored interactively.

---

## Typical static dispatching flow

The current MVP supports a one-shot optimization workflow:

1. Create forklift types and forklifts — the vehicles that execute work.
2. Create storage bins — the warehouse locations.
3. Create load units — the goods that need to be moved.
4. Create transport orders — work items describing movements.
5. Submit a dispatch job — the solver runs asynchronously.
6. Poll the job status — monitor progress until completion.
7. Inspect the result — check forklift assignments and solver score.

This flow is **static**: the solver takes a snapshot of the current warehouse state. If the warehouse changes after a job starts, the plan is not automatically updated. See [Optimization Approach](optimization.md) for the architectural context.

---

## Step-by-step walkthrough

### 1. Create forklift types

Forklift types define the technical capabilities of your fleet (model name, capacity, equipment category, energy specs).

**Endpoint:** `POST /api/v1/forklift-types`

Example fields: `modelName`, `equipmentType`, `maxCapacityKg`, `totalBatteryCapacitykWh`, `baseEnergyConsumptionPerMeter`.

```bash
# List all forklift types (paginated)
GET /api/v1/forklift-types
```

### 2. Register forklifts

Each forklift belongs to a forklift type, has a unique fleet number, and can be assigned a current location and operational status.

**Endpoint:** `POST /api/v1/forklifts`

Example fields: `fleetNumber`, `forkliftTypeId`, `currentStorageBinId` (optional), `status`, `currentBatteryPercentage`.

Additional forklift endpoints:

| Method | Path | Purpose |
|--------|------|---------|
| `GET` | `/api/v1/forklifts` | List all (paginated, 15 per page) |
| `GET` | `/api/v1/forklifts/{id}` | Get by ID |
| `GET` | `/api/v1/forklifts/search?minCapacity=…&status=…` | Filter by capacity or status |
| `PUT` | `/api/v1/forklifts/{id}/location` | Move to a different storage bin |
| `PATCH` | `/api/v1/forklifts/{id}/status?status=…` | Change operational status |

### 3. Create storage bins

Storage bins are warehouse locations identified by a code and a 3D coordinate. Each bin belongs to a zone type (e.g., `STORAGE`, `STAGING_IN`, `STAGING_OUT`).

**Endpoint:** `POST /api/v1/storage-bins`

Example fields: `binCode`, `coordinate` (x, y, z), `zoneType`, `maxWeightCapacityKg`.

```bash
# List all storage bins (paginated)
GET /api/v1/storage-bins
```

### 4. Register load units

Load units represent physical goods stored in the warehouse. Each has a unique tracking code, a weight, a status, and an optional storage bin location.

**Endpoint:** `POST /api/v1/load-units`

Example fields: `trackingCode`, `weightKg`, `status`, `currentStorageBinId`.

Additional load unit endpoints:

| Method | Path | Purpose |
|--------|------|---------|
| `GET` | `/api/v1/load-units` | List all (paginated) |
| `GET` | `/api/v1/load-units/{id}` | Get by ID |
| `GET` | `/api/v1/load-units/tracking/{trackingCode}` | Find by tracking code |
| `GET` | `/api/v1/load-units/status/{status}` | Filter by status |

### 5. Create transport orders

A transport order describes a movement: move a specific load unit from a source bin to a destination bin. You can optionally specify a required equipment type, which the solver will respect when assigning forklifts.

**Endpoint:** `POST /api/v1/transport-orders`

Example fields: `targetLoadUnitId`, `sourceBinId`, `destinationBinId`, `requiredEquipment` (optional).

Additional transport order endpoints:

| Method | Path | Purpose |
|--------|------|---------|
| `GET` | `/api/v1/transport-orders/{id}` | Get by ID |
| `GET` | `/api/v1/transport-orders/search?status=…&minWeight=…` | Search with filters |
| `PUT` | `/api/v1/transport-orders/{id}/status` | Update status along lifecycle |

### 6. Submit a dispatch job

Once the warehouse state is ready, submit a dispatch job. The solver runs asynchronously and returns a job ID for tracking.

**Endpoint:** `POST /api/v1/dispatcher/jobs`

```json
// Response (HTTP 202)
{
  "jobId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
}
```

The job progresses through states: `QUEUED` → `SOLVING` → `COMPLETED` (or `FAILED` / `ABORTED`).

### 7. Poll job status

Use the job ID to check progress:

**Endpoint:** `GET /api/v1/dispatcher/jobs/{jobId}`

```json
{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "status": "COMPLETED",
  "createdAt": "2026-06-13T14:30:00Z",
  "completedAt": "2026-06-13T14:30:05Z",
  "finalScore": "0hard/-150soft"
}
```

Wait until the status is `COMPLETED` before inspecting assignments.

### 8. Terminate a job (optional)

A running or queued job can be aborted:

**Endpoint:** `DELETE /api/v1/dispatcher/jobs/{jobId}`

Returns `204 No Content` on success. The job is marked as `ABORTED` and partial results are discarded.

---

## Seeded development data

When the application starts with the `dev` profile (default in Docker Compose), the `DataSeeder` populates the database with sample data:

- 2 forklift types (a side loader and a reach truck)
- 4 storage bins (dock, two storage zones, shipping)
- 3 load units of varying weights
- 2 forklifts
- 3 transport orders (one already assigned, two open)

This enables testing of the dispatch flow immediately without creating data manually.

---

## Endpoint summary

| Group | Base path | Endpoints |
|-------|-----------|-----------|
| Forklift Types | `/api/v1/forklift-types` | `GET /`, `GET /{id}`, `POST /` |
| Forklifts | `/api/v1/forklifts` | `GET /`, `GET /{id}`, `GET /search`, `POST /`, `PUT /{id}/location`, `PATCH /{id}/status` |
| Storage Bins | `/api/v1/storage-bins` | `GET /`, `GET /{id}`, `POST /` |
| Load Units | `/api/v1/load-units` | `GET /`, `GET /{id}`, `GET /tracking/{code}`, `GET /status/{status}`, `POST /` |
| Transport Orders | `/api/v1/transport-orders` | `GET /{id}`, `GET /search`, `POST /`, `PUT /{id}/status` |
| Dispatcher | `/api/v1/dispatcher/jobs` | `POST /`, `GET /{jobId}`, `DELETE /{jobId}` |

---

## Current limitations

- **No authentication or authorization.** All endpoints are publicly accessible. Security is out of scope for the current MVP.
- **Static one-shot dispatching.** The solver processes a warehouse snapshot. It does not react to changes in real time.
- **Simplified warehouse topology.** Distances are estimated using 3D coordinates. The system does not model aisles, one-way paths, or blocked zones.
- **Limited constraint set.** Three constraints are active: forklift capacity, equipment compatibility, and travel distance. See [Optimization Approach](optimization.md) for the full constraint model.
- **Not production-ready.** The API is designed for local development and portfolio demonstration, not for a live warehouse operations environment.

See [Known Limitations](limitations.md) for a more detailed discussion.
