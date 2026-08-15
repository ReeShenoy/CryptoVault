# CryptoVault – Cryptography Demonstration System

A full-stack college project that demonstrates three cryptographic concepts using **real
Java Cryptography Architecture (JCA) APIs** — nothing here is faked or hardcoded:

1. **RSA** – Asymmetric Encryption
2. **Diffie–Hellman** – Key Exchange
3. **DSA** – Digital Signatures

Every key, ciphertext, shared secret, and signature you see in the UI is generated live by
the Spring Boot backend.

```
cryptovault/
├── backend/    Spring Boot REST API (Java, JCA, MySQL)
└── frontend/   React UI
```

---

## 1. Prerequisites

- **Java 17+** (`java -version`)
- **Maven 3.8+** (`mvn -version`)
- **Node.js 18+ and npm** (`node -v`, `npm -v`)
- **MySQL 8+** running locally

---

## 2. Set up the database

Log into MySQL and create the database (the app can also auto-create it, but it's safer to
do it explicitly first):

```sql
CREATE DATABASE cryptovault;
```

Open `backend/src/main/resources/application.properties` and set your own MySQL
username/password if they differ from the defaults:

```properties
spring.datasource.username=your_username
spring.datasource.password=your_password
```

Hibernate is set to `ddl-auto=update`, so the `crypto_operations` table is created
automatically the first time the backend starts — you don't need to write any DDL yourself.

---

## 3. Run the backend

```bash
cd backend
mvn spring-boot:run
```

The API starts on **http://localhost:8081**. You should see Spring Boot's startup banner
and a Hibernate log confirming the `crypto_operations` table was created/verified.

Quick sanity check once it's up:

```bash
curl -X POST http://localhost:8080/api/rsa/generate-keys
```

You should get back a JSON object with `publicKey`, `privateKey`, and `keySize`.

### Backend endpoints

| Module | Method | Endpoint |
|---|---|---|
| RSA | POST | `/api/rsa/generate-keys` |
| RSA | POST | `/api/rsa/encrypt` |
| RSA | POST | `/api/rsa/decrypt` |
| Diffie-Hellman | POST | `/api/dh/generate-keys` |
| Diffie-Hellman | POST | `/api/dh/generate-shared-secret` |
| DSA | POST | `/api/dsa/generate-keys` |
| DSA | POST | `/api/dsa/sign` |
| DSA | POST | `/api/dsa/verify` |
| Operation log | GET | `/api/operations` |

**Note on Diffie-Hellman key generation:** generating fresh 2048-bit DH domain parameters
(the prime modulus `p` and generator `g`) involves a slow prime-search algorithm inside the
JDK. The first `/api/dh/generate-keys` call can take anywhere from a couple of seconds up to
around 30 seconds depending on your machine — this is normal and is why the button shows a
"Generating…" state. RSA and DSA key generation are fast by comparison.

---

## 4. Run the frontend

In a second terminal:

```bash
cd frontend
npm install
npm start
```

The React app opens on **http://localhost:3000** and talks to the backend at
`http://localhost:8081` (configured in `frontend/src/api/client.js`).

---

## 5. Demo walkthrough

**Landing page → "Explore CryptoVault" → Dashboard → pick a module.**

### RSA
1. Type a short message (keep it under ~190 bytes — RSA-2048/OAEP can only encrypt small
   payloads directly, which the UI explains).
2. Click **Generate Keys** → a real 2048-bit RSA key pair appears.
3. Click **Encrypt** → the backend encrypts with the public key.
4. Click **Decrypt** → the backend decrypts with the private key and the original text
   reappears.

### Diffie–Hellman
1. Click **Generate Keys** → the backend generates Alice's DH key pair, then generates
   Bob's key pair using the *same* domain parameters (this is required for DH to work, just
   like in a real exchange).
2. Click **Exchange & Derive** → Alice's private key + Bob's public key, and Bob's private
   key + Alice's public key, are combined independently.
3. The UI shows **"Shared secrets match ✓"** — proving both parties reached the same secret
   without it ever being transmitted directly.

### DSA
1. Type a message, click **Generate Keys**, then **Sign Message**.
2. Click **Verify Signature** → **"Signature Valid ✓"**.
3. Now edit the message text and click **Verify Signature** again → **"Signature Invalid ✗"**,
   demonstrating that the signature is bound to the exact original content.

---

## 6. Database logging

Every operation (key generation, encrypt, decrypt, key exchange, sign, verify) is written to
the `crypto_operations` table via `OperationLogService`. You can inspect the log two ways:

```sql
SELECT * FROM crypto_operations ORDER BY timestamp DESC;
```

or via the API:

```bash
curl http://localhost:8080/api/operations
```

---

## 7. Project structure reference

```
backend/src/main/java/com/cryptovault/
├── CryptoVaultApplication.java
├── config/
│   ├── CorsConfig.java              # allows the React dev server to call the API
│   └── GlobalExceptionHandler.java  # consistent JSON error responses
├── controller/                      # REST endpoints (RsaController, DhController, DsaController, OperationController)
├── service/                         # cryptographic logic + operation logging (RsaService, DhService, DsaService, OperationLogService)
├── model/                           # CryptoOperation JPA entity
├── repository/                      # CryptoOperationRepository
└── dto/                             # request/response objects, grouped by module

frontend/src/
├── App.js                # routing
├── index.css              # design tokens + shared styles
├── api/                   # axios calls per module
├── components/            # CopyButton, DataField, StatusBanner, ModuleHeader
└── pages/                 # LandingPage, DashboardPage, RsaModule, DhModule, DsaModule
```

---

## 8. Troubleshooting

- **`403`/connection refused from the frontend** → make sure the backend is running on port
  8080 before starting the frontend.
- **MySQL connection errors** → double-check `application.properties` credentials and that
  MySQL is running (`mysql.server start` or `sudo service mysql start`).
- **CORS errors in the browser console** → confirm the frontend is running on
  `http://localhost:3000` (the backend's `CorsConfig` only allows that origin by default).
- **DH key generation feels stuck** → this is expected the first time; 2048-bit DH parameter
  generation is CPU-intensive. Give it up to ~30 seconds.

---

## 9. 👨‍💻 Author

**Reethu**  

GitHub: https://github.com/ReeShenoy

---
