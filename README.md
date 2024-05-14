# Fullstack Project - Samuel Deotti

This project aims to develop a fullstack application for a vinyl record e-commerce platform. Users can view, search, and buy albums, add them to their collection, view their album collection, and remove albums from the collection. The application includes both a front-end and a back-end, with APIs for album management and user management.

## Frontend

### How to Build and Run the Frontend Application

1. Clone the project from GitHub:
    ```bash
    git clone https://github.com/bc-fullstack-04/samuel-deotti-frontend.git
    ```
   or
    ```bash
    git clone git@github.com:bc-fullstack-04/samuel-deotti-frontend.git
    ```

2. Navigate to the project directory:
    ```bash
    cd samuel-deotti-frontend
    ```

3. Start the Docker Compose services:
    ```bash
    docker compose up
    ```

4. In another terminal tab, install the dependencies:
    ```bash
    npm install
    ```

5. Start the development server:
    ```bash
    npm run dev
    ```

6. Wait for a new tab to open in your browser, or open it manually. The application usually runs on port 5173, but it may open on a different port. If you encounter issues, check the terminal for the specified port.
    ```bash
    http://localhost:5173/
    ```

### How to Stop the Frontend Application

To stop the application, press `Ctrl + C` in the terminal where Docker Compose and the development server are running.

### Frontend Routes

- /: `Landing page`
- /login: `User login page`
- /signup: `User signup page`
- /dashboard: `Dashboard for viewing, searching, and buying albums`
- /albums/my-collection: `Page to view and manage the user's album collection`

## Backend

### Loyalty Program

The vinyl record e-commerce has implemented a loyalty program based on points to increase sales volume and attract new customers. After several meetings, the sales team defined a table of points per day of the week:

| Day    | Sunday | Monday | Tuesday | Wednesday | Thursday | Friday | Saturday |
|--------|--------|--------|---------|-----------|----------|--------|----------|
| Points | 25     | 7      | 6       | 2         | 10       | 15     | 20       |

### How to Build and Run the Backend Application

1. Clone the project from GitHub:
    ```bash
    git clone https://github.com/bc-fullstack-04/samuel-deotti-backend.git
    ```
   or
    ```bash
    git clone git@github.com:bc-fullstack-04/samuel-deotti-backend.git
    ```

2. Navigate to the project directory:
    ```bash
    cd samuel-deotti-backend
    ```

3. Build and Run the Docker Compose services:
    ```bash
    docker-compose up --build
    ```

### How to Stop the Backend Application

To stop the application, press `Ctrl + C` in the terminal where Docker Compose is running, or run the following command in the project root directory:
    ```bash
    docker-compose down
    ```

### Backend Ports

- User Management API: `8081`
- Album Management API: `8082`
- PostgreSQL: `5432`
- RabbitMQ Management UI: `15672`
- RabbitMQ: `5672`

### API Documentation

- For documentation on the User Management API, navigate to [User Management API](http://localhost:8081/api/swagger-ui/index.html#/) in your browser.
- For documentation on the Album Management API, navigate to [Album Management API](http://localhost:8082/api/swagger-ui/index.html#/) in your browser.
