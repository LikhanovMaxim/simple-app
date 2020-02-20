import org.jetbrains.kotlin.konan.target.*

plugins {
    kotlin("jvm") version "1.3.60"
    kotlin("multiplatform") version "1.3.60" apply(false)
    application
    java
}

repositories {
    jcenter()
    mavenLocal()
    maven(url = "http://oss.jfrog.org/oss-release-local")
}

val drillAgentDist: Configuration by configurations.creating

object DrillAgent {
    val version = "0.5.0-20"
    val platform = HostManager.host.presetName
    val file = "drill_agent.dll"
}
dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.eclipse.jetty:jetty-servlet:9.4.6.v20170531")
    implementation("javax.servlet:javax.servlet-api:3.1.0")
    implementation("ch.qos.logback:logback-classic:1.2.3")
    implementation("io.github.microutils:kotlin-logging:1.7.8")
    drillAgentDist("com.epam.drill:drill-agent-${DrillAgent.platform}:${DrillAgent.version}")
}

val agentsBaseDir = file("$buildDir/agents")
val agentDir = file("$agentsBaseDir/${DrillAgent.platform}-${DrillAgent.version}")
val agentPath = file("$agentDir/${DrillAgent.file}")

object AgentParams {
    val adminAddress = "localhost:8090"
    val agentId = "simple-jetty"
    val serviceGroupId = ""
    val buildVersion = "0.3.0"
    override fun toString(): String = listOf(::adminAddress, ::agentId, ::serviceGroupId, ::buildVersion)
            .filter { it.get().isNotEmpty() }.joinToString(separator = ",") { "${it.name}=${it.get()}" }
}

application {
    mainClassName = "org.springframework.samples.petclinic.HelloWorldKt" //TODO package
    applicationDefaultJvmArgs = listOf(
            "-javaagent:${agentDir}/drill-proxy.jar=ttl.agent.logger:STDOUT",
            "-agentpath:$agentPath=drillInstallationDir=$agentDir,$AgentParams"
    )
}

tasks {
    val copyAgentDist by registering(Copy::class) {
        into(agentsBaseDir)
        from(drillAgentDist.files.map(::zipTree))
    }
    (run) {
        dependsOn(copyAgentDist)
    }
    test {
        useJUnitPlatform()
    }
}
