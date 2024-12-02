package com.sptest.project_SP_TroodTest.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.context.annotation.Configuration

/**
 * Swagger Configuration for the Trood test assignment done by Sergejs Ponomarenko of Tel-Ran
 *
 *
 *
 *  USE http://localhost:8080/swagger-ui/index.html TO ACCESS SWAGGER UI!
 *
 *
 */
@OpenAPIDefinition(
    info = Info(
        title = "Trood test assignment - Sergejs Ponomarenko",
        description = "" +
                "This project has been developed as a test assignment for the Trood Company.<br>" +
                "<br>" +
                "I. Entities<br>" +
                "   1) Profile. The basic entity representing a user's profile. All fields required as per the task <br>" +
                "have been implemented, and required constraints have been enforced on the respective fields using both <br>" +
                "annotations in the field class and the appropriate in-code safety locks. The author has added extra fields, <br>" +
                "login and password, to allow authentication-based access to appropriate functions as an extra. <br>" +
                "   2) InterestEntry. An auxiliary entity introduced by the author to make the code potentially more viable in terms <br>" +
                "of any future development. <br>" +
                "<br>" +
                "II. Functionality<br>" +
                "The project features three functions implemented as per the assignment, and another one added as an extra feature. <br>" +
                "   1) Viewing a profile <br>" +
                "   2) Updating a profile <br>" +
                "   3) Updating one's avatar <br>" +
                "   4) (extra) Creating a new profile <br>" +
                "<br>" +
                "III. Extra features. <br>" +
                "The author has added the following extra features to complement the scope of the test project: <br>" +
                "   1) Data Loader - a class that populates the database with 5 test entities upon first startup, in order to facilitate testing <br>" +
                "   2) Access Control - updating a profile or avatar requires one to be authenticated as the respective user or as the Admin. Private profiles can only be viewed by their owners or the Admin, Public ones are available to anyone.<br>" +
                "   3) XSS protection and CORS support added<br>" +
                "   4) The project can also create a Docker container, but this feature has been disabled in order to facilitate testing.",

        version = "1.0.0",
        contact = Contact(
            name = "Sergejs Ponomarenko",
            url = "https://www.emendatus.lv/"
        )
    )
)
@Configuration
class SwaggerConfig {

}