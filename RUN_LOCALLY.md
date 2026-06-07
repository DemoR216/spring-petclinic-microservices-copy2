# Running Spring PetClinic Microservices Locally (Option 1)

This guide walks you through running the application without Docker, using 6 separate terminal windows.

## Prerequisites

- **Java 11 or higher** - Verify with: `java -version`
- **Maven** - Verify with: `mvn --version`

## Setup Instructions

### Step 1: Clone/Navigate to Repository
```bash
cd spring-petclinic-microservices-copy
```

### Step 2: Start Services in Separate Terminals

**Order matters!** Start the supporting services first, then the microservices.

#### Terminal 1: Config Server (Required - Start First)
```bash
cd spring-petclinic-config-server
../mvnw spring-boot:run
```
**Expected output:** "Started ConfigServerApplication"
**Port:** http://localhost:8888

#### Terminal 2: Discovery Server / Eureka (Required - Start Second)
```bash
cd spring-petclinic-discovery-server
../mvnw spring-boot:run
```
**Expected output:** "Started DiscoveryServerApplication"
**Port:** http://localhost:8761

#### Terminal 3: API Gateway (Required - Start Third)
```bash
cd spring-petclinic-api-gateway
../mvnw spring-boot:run
```
**Expected output:** "Started ApiGatewayApplication"
**Port:** http://localhost:8080 (Frontend UI)

#### Terminal 4: Customers Service
```bash
cd spring-petclinic-customers-service
../mvnw spring-boot:run
```
**Expected output:** "Started CustomersServiceApplication"
**Port:** Random (registered with Eureka)

#### Terminal 5: Vets Service
```bash
cd spring-petclinic-vets-service
../mvnw spring-boot:run
```
**Expected output:** "Started VetsServiceApplication"
**Port:** Random (registered with Eureka)

#### Terminal 6: Visits Service
```bash
cd spring-petclinic-visits-service
../mvnw spring-boot:run
```
**Expected output:** "Started VisitsServiceApplication"
**Port:** Random (registered with Eureka)

## Accessing the Application

Once all services are running, open your browser:

### Main Application
- **URL:** http://localhost:8080
- **What:** PetClinic frontend (AngularJS UI)

### Monitoring & Administration

| Service | URL | Purpose |
|---------|-----|---------|
| **Eureka Dashboard** | http://localhost:8761 | View all registered services |
| **Config Server** | http://localhost:8888 | View configuration properties |

## Verify All Services are Running

1. Open http://localhost:8761 (Eureka Dashboard)
2. You should see:
   - `CONFIG-SERVER`
   - `DISCOVERY-SERVER`
   - `API-GATEWAY`
   - `CUSTOMERS-SERVICE`
   - `VETS-SERVICE`
   - `VISITS-SERVICE`

All should have status "UP" in green.

## Troubleshooting

### Port Already in Use
If a port is already in use, you'll see an error like `Address already in use`. 
- Either kill the process using that port or wait for services to finish starting.

### Service Won't Start
- Check that Java version is 11+: `java -version`
- Ensure previous terminals completed startup before starting next ones
- Check network connectivity

### Gateway Timeouts on First Access
- Don't panic! Initial Spring Cloud Gateway timeouts are normal.
- Wait a few seconds for services to register with Eureka.
- Refresh your browser.

### Stopping All Services
- Press `Ctrl+C` in each terminal window

## Database

- **Default:** HSQLDB (in-memory, data cleared on restart)
- **Optional:** MySQL setup available (see main README.md for details)

## Next Steps

Once running:
1. Navigate to http://localhost:8080
2. Add owners and pets
3. Schedule visits
4. Monitor services on http://localhost:8761
