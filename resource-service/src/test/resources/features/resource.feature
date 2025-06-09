Feature: Resource

  Scenario: Upload resource
    When I upload resource with the following content from file /test-mp3/valid-sample-with-required-tags.mp3
    Then Resource is saved
    And Message is send to message broker