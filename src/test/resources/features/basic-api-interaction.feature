Feature: Should support basic REST functionality when interacting with the key/value store.

  The API must support the basic operations of a key/value store, including retrieval of values
  (via GET requests), storage of values (via PUT requests) and removal of values (via DELETE
  requests).

  Scenario Outline: Retrieving stored values from the key/value store via GET requests.
    Given some test data in our store
    When we do a GET request for "<key>"
    Then we should receive a successful response of "<response>"

    Examples:
      | key     | response |
      | name    | Michael  |
      | surname | Anderson |
      | 1234    | 5678     |


  Scenario Outline: We should be able to store data via PUT requests successfully.
    Given an empty data store
    When we do a PUT request to store "<value>" in key "<key>"
    Then we should receive an HTTP status code of "<statusCode>"
    And we should find the value of "<key>" to be "<value>"

    Examples:
      | key              | value     | statusCode |
      | abcd             | efgh      | 200        |
      | something%40else | other     | 200        |
      | something.else   | hello     | 200        |