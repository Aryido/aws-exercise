# AWS kinesis stream with SpringCloud Streaming framework Application

The demo project is used spring cloud stream binding framework to interact with AWS kinesis.

## Goal
1. Build a kinesis stream and s3 bucket by cloudFormation.

2. Send protobuf into kinesis stream.

3. Consume protobuf from kinesis stream and then transform it into parquet file and put it on s3.

4. Download parquet file from s3 and read it.

## Architecture

![image](https://user-images.githubusercontent.com/60870275/168468101-3fdd1da5-c850-4882-a06b-7a935ae6f996.png)

# Usage
- Step1. Setting credential file by AWS-CLI

When we set credential by AWS-CLI, then we don't need to write `sensitive information` in application.yml. For example:
```
cloud:
  aws:
    credentials:
      profile-name: default
      #access-key: not necessary if you have set by AWS-CLI
      #secret-key: not necessary if you have set by AWS-CLI

    region:
      static: ap-northeast-1
    stack:
      auto: false
```

- Step2. Build parent pom

Protobuf class will be generated when we builded pom every time.
```
<build>
	<plugins>
		<plugin>
			<groupId>org.xolstice.maven.plugins</groupId>
			<artifactId>protobuf-maven-plugin</artifactId>
			<version>0.6.1</version>
			<configuration>
				<protocExecutable>protoc</protocExecutable>
				<outputDirectory>${project.basedir}/src/main/java/</outputDirectory>
				<clearOutputDirectory>false</clearOutputDirectory>
			</configuration>
			<executions>
				<execution>
					<goals>
						<goal>compile</goal>
						<goal>test-compile</goal>
					</goals>
				</execution>
			</executions>
		</plugin>
	</plugins>
</build>
```

- Step3. Run kinesis-consumer, kinesis-producer, parquet-reader application

# FAQ
- Parquet-mr doesn't work.

HADOOP_HOME and hadoop.home.dir are unset. [Problems running Hadoop on Windows](https://cwiki.apache.org/confluence/display/HADOOP2/WindowsProblems)


# Reference
[AWS Configuration And Credential File Settings](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-files.html)

[Spring-Cloud-Stream-Binder-AWS-Kinesis](https://github.com/spring-cloud/spring-cloud-stream-binder-aws-kinesis/blob/main/spring-cloud-stream-binder-kinesis-docs/src/main/asciidoc/overview.adoc)

[Spring-Cloud-Stream](https://spring.io/projects/spring-cloud-stream#samples)

[Protobuf Maven Plug](https://www.xolstice.org/protobuf-maven-plugin/usage.html)

[Parquet MR](https://github.com/apache/parquet-mr)
