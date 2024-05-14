# Backend Project - Samuel Deotti

This project aims to develop an application for a vinyl record e-commerce with two APIs: one for
album management and another for user management. Users can buy albums and add them to their
collection, view their album collection, and remove albums from the collection.

## Loyalty Program

The vinyl record e-commerce has implemented a loyalty program based on points to increase sales
volume and attract new customers. After several meetings, the sales team defined a table of points
per day of the week:

| Day    | Sunday | Monday | Tuesday | Wednesday | Thursday | Friday | Saturday |
|--------|--------|--------|---------|-----------|----------|--------|----------|
| Points | 25     | 7      | 6       | 2         | 10       | 15     | 20       |

## How to Build and Run the Application

1. Clone the project from GitHub:
    ```bash
    git clone https://github.com/bc-fullstack-04/samuel-deotti-backend.git
    ```
   or
    ```bash
    git clone git@github.com:bc-fullstack-04/samuel-deotti-backend.git
    ```

2. Navigate to the root directory of the project:
    ```bash
    cd samuel-deotti-backend
    ```

3. Build and Run the Docker Compose:
    ```bash
    docker-compose up --build
    ```

## How to Stop the Application

To stop the application, simply press `Ctrl + C` in the terminal where Docker Compose is running.
Or run the following command in the root directory of the project:

```bash
docker-compose down
```

## Ports

- User Management API: `8081`
- Album Management API: `8082`
- PostgreSQL: `5432`
- RabbitMQ Management UI: `15672`
- RabbitMQ: `5672`

## API Documentation

- For documentation on the USER management API, navigate
  to [User Management API](http://localhost:8081/api/swagger-ui/index.html#/) in your browser.


- For documentation on the ALBUM management API, navigate
  to [Album Management API](http://localhost:8082/api/swagger-ui/index.html#/) in your browser.