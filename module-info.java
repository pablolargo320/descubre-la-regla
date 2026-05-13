<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.mycompany</groupId>
    <artifactId>Partida1</artifactId>
    <version>1.0-SNAPSHOT</version>
    <name>AdivinaLaRegla</name>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>20</maven.compiler.source>
        <maven.compiler.target>20</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- JavaFX -->
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>21.0.2</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>21.0.2</version>
        </dependency>

        <!-- SQLite embebido (no requiere servidor externo) -->
        <dependency>
            <groupId>org.xerial</groupId>
            <artifactId>sqlite-jdbc</artifactId>
            <version>3.45.1.0</version>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <!-- Compilador Java 20 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.0</version>
                <configuration>
                    <release>20</release>
                </configuration>
            </plugin>

            <!-- Copia todas las dependencias a target/mods para el module path -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-dependency-plugin</artifactId>
                <version>3.3.0</version>
                <executions>
                    <execution>
                        <id>copy-mods</id>
                        <phase>process-classes</phase>
                        <goals><goal>copy-dependencies</goal></goals>
                        <configuration>
                            <outputDirectory>${project.build.directory}/mods</outputDirectory>
                        </configuration>
                    </execution>
                </executions>
            </plugin>

            <!--
                Lanzador para el botón "Run" del IDE (exec:exec con id=default-cli).
                Pone JavaFX y SQLite en el module path y añade ALL-MODULE-PATH
                para que el ServiceLoader de JDBC encuentre el driver SQLite.
            -->
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <version>3.5.1</version>
                <executions>
                    <execution>
                        <id>default-cli</id>
                        <goals><goal>exec</goal></goals>
                        <configuration>
                            <executable>java</executable>
                            <arguments>
                                <argument>--module-path</argument>
                                <argument>${project.build.directory}/mods${path.separator}${project.build.directory}/classes</argument>
                                <argument>--add-modules</argument>
                                <argument>ALL-MODULE-PATH</argument>
                                <argument>-m</argument>
                                <argument>poli.edu.co/poli.edu.co.vista.App</argument>
                            </arguments>
                        </configuration>
                    </execution>
                </executions>
            </plugin>

            <!-- Lanzador alternativo: mvn javafx:run -->
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <configuration>
                    <mainClass>poli.edu.co.vista.App</mainClass>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
