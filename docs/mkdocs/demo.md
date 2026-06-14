# Demo Guide

This page walks through the static dispatching workflow in under 5 minutes using the seeded development data. No manual data entry required.

!!! info "Prerequisites"
    Docker and Docker Compose must be installed. All data is pre-seeded — you only need to start the application.

---

## 1. Start the application

```bash
docker compose up -d
```

Wait a few seconds for PostgreSQL and the API to start. The `dev` profile loads sample data automatically.

---

## 2. Open Swagger UI

Navigate to:

```
http://localhost:8080/swagger-ui.html
```

All steps below can be executed directly in Swagger UI. Each endpoint shows the full request/response schema.

---

## 3. Inspect the seeded warehouse

The `DataSeeder` creates a small warehouse with 2 forklifts, 4 storage bins, 3 load units, and 3 transport orders — 2 of which are open and ready for dispatch.

**List forklifts:**

`GET /api/v1/forklifts`

You should see `FLEET-HEAVY-01` (a side loader at the dock) and `FLEET-REACH-01` (a reach truck in zone A). One is already assigned to an in-progress order.

**List open transport orders:**

`GET /api/v1/transport-orders/search?status=OPEN`

Two open orders are returned — one requiring a side loader (heavy load), one with no specific equipment requirement (light load). These are the work items the solver will assign.

---

## 4. Submit a dispatch job

`POST /api/v1/dispatcher/jobs`

No request body required. The response is `202 Accepted` with a job ID:

```json
{
  "jobId": "a1b2c3d4-e5f6-7890-abcd-ef1234567890"
}
```

The solver starts asynchronously. The job transitions through `QUEUED` → `SOLVING` → `COMPLETED`.

---

## 5. Poll job status

`GET /api/v1/dispatcher/jobs/{jobId}`

Use the `jobId` from step 4. Poll until `status` is `COMPLETED` (typically a few seconds for the seeded dataset):

```json
{
  "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
  "status": "COMPLETED",
  "createdAt": "2026-06-14T12:00:00Z",
  "completedAt": "2026-06-14T12:00:04Z",
  "finalScore": "0hard/-150soft"
}
```

A `finalScore` of `0hard` means no hard constraints were violated — all assignments respect capacity and equipment requirements. The negative soft score reflects the travel distance penalty the solver minimized.

---

## 6. Inspect the result

The solved assignments are persisted to the database. To see them:

**Check transport orders after solving:**

`GET /api/v1/transport-orders/search`

The previously open orders should now show `status: ASSIGNED` and include an `assignedForklift` field. Each forklift received the orders it is capable of handling based on capacity and equipment compatibility.

**Check forklifts after solving:**

`GET /api/v1/forklifts`

Each forklift's `transportOrders` array now reflects the sequence the solver determined.

---

## 7. Stop the environment

```bash
docker compose down
```

---

## What just happened?

| Step       | What the solver did |
|------------|---------------------|
| Load       | Read all forklifts, storage bins, load units, and open transport orders |
| Constraint | Enforced that no forklift receives an order exceeding its capacity or requiring incompatible equipment |
| Optimize   | Sequenced orders to minimize total estimated travel distance |
| Persist    | Wrote the solved assignments back to forklifts and transport orders |

This is a **static** dispatch — the solver took a snapshot of the current warehouse state. If the warehouse changes later, a new job must be submitted.

---

## Explore further

- OpenAPI reference: `http://localhost:8080/swagger-ui.html`
- Postman collection: `docs/postman/lift-nexus-api-v0.1.0.postman_collection.json`
- [API Usage](api-usage.md) — full endpoint reference
- [Optimization Approach](optimization.md) — how the solver works
