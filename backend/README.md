# Backend API

This is a basic HTTP API for the FormalCalculation project, built with cats-effect and http4s.

## Endpoints

### Health Check
- `GET /health` - Returns simple health status

### Arithmetic Operations
- `GET /arithmetic/add/{a}/{b}` - Add two integers
- `GET /arithmetic/subtract/{a}/{b}` - Subtract two integers  
- `GET /arithmetic/multiply/{a}/{b}` - Multiply two integers
- `GET /arithmetic/divide/{a}/{b}` - Divide two integers (returns quotient and remainder)

### Natural Number Operations
- `GET /natural/factorial/{n}` - Calculate factorial of n

## Running the Server

```bash
# Compile the backend
sbt backend/compile

# Run the server (starts on port 8080)
sbt backend/run

# Run tests
sbt backend/test
```

## Example Usage

```bash
# Health check
curl http://localhost:8080/health

# Add 5 + 3
curl http://localhost:8080/arithmetic/add/5/3

# Calculate 5!
curl http://localhost:8080/natural/factorial/5

# Divide 10 by 3
curl http://localhost:8080/arithmetic/divide/10/3
```

## Response Format

All responses are in JSON format:

```json
{
  "operation": "add",
  "operands": [5, 3],
  "result": 8
}
```

Division returns both quotient and remainder:

```json
{
  "operation": "divide", 
  "operands": [10, 3],
  "quotient": 3,
  "remainder": 1
}
```