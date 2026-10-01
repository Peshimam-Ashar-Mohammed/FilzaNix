package com.filzanix.filesystem;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class VirtualFileSystem {

    private final Path root;
    private Path currentDirectory;

    public VirtualFileSystem() throws IOException {

        this.root = Paths.get("env").toAbsolutePath().normalize();

        Files.createDirectories(root.resolve("home"));
        Files.createDirectories(root.resolve("etc"));
        Files.createDirectories(root.resolve("tmp"));
        Files.createDirectories(root.resolve("root"));

        this.currentDirectory = root.resolve("home");
    }

    public String getCurrentPath() {

        return "/" + root.relativize(currentDirectory)
                .toString()
                .replace("\\", "/");
    }

    public void listFiles() throws IOException {

        try (var files = Files.list(currentDirectory)) {

            files.forEach(path -> System.out.println(
                    path.getFileName()
            ));
        }
    }
}