# TODO List API rest-api-assignment b2boost
Date : 23-24 nov 2021

- [x] 1. The endpoint will return custom error JSON messages in the payload,  additionally to the standard HTTP response codes similar to this one:

   ```
  {
    "code": 404,
    "message": "Partner with id 1 not found!"
  }
   ```

- [x] 2. The application will be cleanly layered, by separating control and  business logic. For instance, the controller of the endpoint will not  contain any business logic, and will limit itself to

  - marshalling data from the http layer to the service layer
  - reporting error conditions in the response
  - marshalling results back to the http layer, including custom errors

- [x] 3. The service layer will be transactional and encapsulate all validation and database interactions

- [x] 4. The data layer will be implemented by using a data repository service

- [x] 5. The application can run with an embedded in-memory database

- [ ] 6. The application will have a health check endpoint

- [ ] 7. The application will have suitable functional tests, checking real http functionality

- [x] 8. No authentication/security necessary

- [ ] This document will contain the specification of the REST endpoint, with data definition and error payload specification.

You should document how to

- [ ] Run the test suite
- [ ] Run application
- [ ] Optionally, a commentary on how you would deploy it (not necessary to implement this)