# hmpps-ciag-careers-induction-api
[![repo standards badge](https://img.shields.io/badge/dynamic/json?color=blue&style=flat&logo=github&label=MoJ%20Compliant&query=%24.result&url=https%3A%2F%2Foperations-engineering-reports.cloud-platform.service.justice.gov.uk%2Fapi%2Fv1%2Fcompliant_public_repositories%2Fhmpps-ciag-careers-induction-api)](https://operations-engineering-reports.cloud-platform.service.justice.gov.uk/public-github-repositories.html#hmpps-ciag-careers-induction-api "Link to report")
[![CircleCI](https://circleci.com/gh/ministryofjustice/hmpps-ciag-careers-induction-api/tree/main.svg?style=svg)](https://circleci.com/gh/ministryofjustice/hmpps-ciag-careers-induction-api)
[![Docker Repository on Quay](https://quay.io/repository/hmpps/hmpps-ciag-careers-induction-api/status "Docker Repository on Quay")](https://quay.io/repository/hmpps/hmpps-ciag-careers-induction-api)
[![API docs](https://img.shields.io/badge/API_docs_-view-85EA2D.svg?logo=swagger)](https://ciag-induction-api-dev.hmpps.service.justice.gov.uk/swagger-ui/index.html?configUrl=/v3/api-docs)


# Instructions
## Configuring the project

### Ktlint formatting
Ktlint is used to format the source code and a task runs in the Circle build to check the formatting.

You should run the following commands to make sure that the source code is formatted locally before it breaks the Circle build.

#### Apply ktlint formatting rules to Intellij
`./gradlew ktlintApplyToIdea`

Or to apply to all Intellij projects:

`./gradlew ktlintApplyToIdeaGlobally`

#### Run ktlint formatter on git commit
`./gradlew addKtlintFormatGitPreCommitHook`

## Running the app
The easiest (and slowest) way to run the app is to use docker compose to create the service and all dependencies.

`docker-compose pull`

`docker-compose up`

* See `http://localhost:8081/health` to check the app is running.
* See `http://localhost:8081/swagger-ui/index.html?configUrl=/v3/api-docs` to explore the OpenAPI spec document.

## Environment variables
The following environment variables are required in order for the app to start:

### General

| Name           | Description                                |
|----------------|--------------------------------------------|
| SERVER_PORT    | The port that the application will run on  |
| HMPPS_AUTH_URL | The URL for OAuth 2.0 authorisation server |

### Database

| Name      | Description                       |
|-----------|-----------------------------------|
| DB_SERVER | The host of the DB server         |
| DB_NAME   | The name of the database instance |        
| DB_USER   | The application's DB username     |
| DB_PASS   | The DB user's password            |

### Application Insights

| Name                                   | Description                              |
|----------------------------------------|------------------------------------------|
| APPINSIGHTS_INSTRUMENTATIONKEY         | The instrumentation key for App Insights |
| APPLICATIONINSIGHTS_CONNECTION_STRING  | The connection string for App Insights   |
| APPLICATIONINSIGHTS_CONFIGURATION_FILE | A configuration file for App Insights    |

### SQS/SNS Topics and Queues

| Name                                             | Description                                                               |
|--------------------------------------------------|---------------------------------------------------------------------------|
| HMPPS_SQS_USE_WEB_TOKEN                          | Set to `true` if the `DefaultAWSCredentialsProviderChain` should be used. |
| HMPPS_SQS_TOPICS_DOMAINEVENTS_ARN                | The AWS ARN for the shared domain-events topic.                           |

## Monitoring, tracing and event reporting
The API is instrumented with the opentelemetry and Application Insights java agent. Useful data can be found and reported
on via the Azure Application Insights console, all under the `cloud_RoleName` property of `hmpps-ciag-careers-induction-api`

## To run locally 
Bring the docker images up using the below command
`docker-compose up --scale   hmpps-ciag-careers-induction-api=0`

set localstack as active profile

set the environment variable to
API_BASE_URL_OAUTH = https://sign-in-dev.hmpps.service.justice.gov.uk/auth

The configuration file can be retrieved from .run/HmppsCiagCareersInductionApi.run.xml
