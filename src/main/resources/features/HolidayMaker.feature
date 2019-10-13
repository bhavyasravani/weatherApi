Feature: To plan a happy holiday

  Scenario: A happy holiday maker
    Given I like to holiday in "North Sydney"
    And I only like to holiday on "MONDAY"
    When I look up the weather forecast
    Then I receive the weather forecast
    And the temperature is warmer than 10 degrees