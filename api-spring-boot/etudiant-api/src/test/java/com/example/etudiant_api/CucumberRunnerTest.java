package com.example.etudiant_api;

import org.junit.platform.suite.api.*;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(
        key = "cucumber.glue",
        value = "com.example.etudiant_api.steps"
)
public class CucumberRunnerTest {}