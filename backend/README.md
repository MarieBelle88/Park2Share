# Backend Setup for Park2Share

This documentation guides you through setting up and running the backend for the Park2Share project.

## Prerequisites

Before proceeding, ensure you have the following installed on your system:

- [Docker](https://www.docker.com/get-started)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)

---

## Running the Backend


### 1. Open Project in IntelliJ IDEA

- Open IntelliJ IDEA.
- Select `Open` and choose the `backend` folder from the cloned repository.
- Let IntelliJ index and analyze the project files. During this process, review the `build.gradle` and related Gradle configuration files to understand dependencies.

---

### 3. Set Up the MySQL Database with Docker

Pull the MySQL Docker image and create a container:
```bash
docker pull mysql:latest
docker run --name mysql-park2share -e MYSQL_ROOT_PASSWORD=secret -p 3306:3306 -d mysql:latest
```
- **Container Name**: `mysql-park2share` (you can change this if needed).
- **Port Mapping**: Maps the MySQL service to `localhost:3306`.
- **Environment Variable**: `MYSQL_ROOT_PASSWORD` is set to `secret`.

> **Note**: Ensure the Docker service is running and accessible.

---

### 4. Run the API

After IntelliJ has finished indexing:

1. Navigate to the `Application.kt` file in the backend source folder.
2. Click the `Play` button at the top to start the API.
3. The API should now be running on:
    - **URL**: `http://127.0.0.1:8080` or `http://localhost:8080`

---

### 5. Database Initialization

When the API starts, it will:

- Automatically create the required database in the container.
- Generate all necessary tables.
- Populate the tables with initial data.

You can verify this by connecting to the MySQL database:
```bash
docker exec -it mysql-park2share mysql -u root -p
```
- Use the password `secret` to log in.
- Run `SHOW DATABASES;` to see the `park2share` database.

---

### 6. Verify the Setup

Access the API base URL:
```bash
http://127.0.0.1:8080
```
You should see a response confirming the API is running.

---

### Next Steps

Once the backend is running, you can:

- Test the endpoints using tools like Postman or cURL.
- Endpoints (to be added).

### Frontend
- After activating it (check the frontend readme file):
  - You can use these logins (or sign up): **email**: test@gmail.com , **password**: whatcouldthisbe123