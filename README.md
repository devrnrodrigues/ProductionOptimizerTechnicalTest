## Production Optimizer – Run Guide

This project consists of:

- **Backend**: REST API in Spring Boot (Java 21), responsible for business rules and production optimization.
- **Frontend**: SPA in Vue 3 + Vite, responsible for the user interface.

### 1. Prerequisites

- **Java 21+** (JDK) installed and configured on your `PATH`.
- **Maven 3.9+** (optional, as the project includes the wrapper `mvnw` / `mvnw.cmd`).
- **Node.js 18+** and **npm** installed.
- A **PostgreSQL** database available, configured according to `src/main/resources/application.yaml`.

---

### 2. Backend (Spring Boot)

From the project root directory (`ProductionOptimizerTechnicalTest`):

- **Install dependencies and build** (optional, but recommended the first time):

```bash
./mvnw clean install        # Linux/macOS
mvnw.cmd clean install      # Windows
```

- **Run the application**:

```bash
./mvnw spring-boot:run      # Linux/macOS
mvnw.cmd spring-boot:run    # Windows
```

The API will be available by default at `http://localhost:8080`.

- **Run backend tests**:

```bash
./mvnw test                 # Linux/macOS
mvnw.cmd test               # Windows
```

---

### 3. Frontend (Vue 3 + Vite)

From the project root, go to the frontend folder:

```bash
cd frontend
```

- **Install dependencies** (only the first time or when `package.json` changes):

```bash
npm install
```

- **Run the development server**:

```bash
npm run dev
```

By default Vite runs at something like `http://localhost:5173` (the exact URL is shown in the terminal).  
Make sure the backend (`http://localhost:8080`) is running so the web app can consume the API.

---

### 4. Frontend unit tests

Unit tests are set up in the frontend using **Vitest**.

- **Run all tests once**:

```bash
cd frontend
npm test
```

- **Run tests in watch mode** (re-runs when files are saved):

```bash
cd frontend
npm run test:watch
```

Current tests cover the API client services:

- `src/services/productService.js`
- `src/services/rawMaterialService.js`
- `src/services/productionService.js`

You can add more tests by creating `*.test.js` files under `frontend/tests`.

---

### 5. Full flow (run everything)

1. **Start PostgreSQL** and ensure credentials and URL in `application.yaml` are correct.
2. **Start the backend**:
   - From the project root:
     - `mvnw.cmd spring-boot:run` (Windows) or `./mvnw spring-boot:run` (Linux/macOS).
3. **Start the frontend**:
   - From the `frontend` directory:
     - `npm install` (first time only).
     - `npm run dev`.
4. Open the frontend in your browser (e.g. `http://localhost:5173`) and use the application.
