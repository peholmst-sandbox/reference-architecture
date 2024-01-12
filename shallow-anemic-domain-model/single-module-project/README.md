# Single Module Project with Anemic/Shallow Domain Model

This is an example of a Vaadin application with a very simple domain model and a heavy reliance of the Spring
framework.

* Entities only contain getters and setters and are used directly in the UI.
* The business logic is in the service layer. There are no value objects.
* Bean validation is used for validation both in the UI and in the service layer (implicitly).
* JPA criteria and Spring Data Specifications are used for one special query.
* The Spring Data Scrolling API is used for lazy loading into the grid.
* There is one scheduled background job that sends out birthday wishes. Following best practices, the job itself and
  the scheduler are in separate Spring beans.
* A custom converter for converting between `ZoneId` and `String` is used both in the UI and in JPA.
* An SPI with a corresponding implementation is used to send mails.
* ArchUnit is used to verify the structure of the application.
 
## Notes:
 
* Vaadin's `BeanValidationBinder` does not interpret `@NotBlank` as required. If the annotation is changed to `@NotNull`, the  
  validation is effectively rendered useless as the default value is an empty string, which is not `null`.
* Vaadin's `BeanValidationBinder` does not work properly with `@NotNull` and a custom `Converter`. The field shows up as  
  invalid all the time, but otherwise behaves properly.
* Writing an integration test for the birthday sending job itself is easy thanks to the separation of concerns, but 
  writing an integration test that checks that it actually runs when it is supposed to (i.e. verifying that the CRON 
  string in `@Scheduled` is correct or that scheduling has even been enabled) is difficult.
* The ArchUnit tests are currently written in such a way that they allow everything, except when forbidden. In other words,
  the rules dictate which dependencies are *not* allowed. Would it make more sense to just declare what the allowed dependencies are
  for each layer?