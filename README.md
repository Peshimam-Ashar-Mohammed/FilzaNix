# FilzaNix

### A Java-powered virtual command-line computing environment.

FilzaNix is an experimental command-line environment developed from scratch using Java 21. Inspired by Linux shells and virtual operating systems, the project aims to provide an isolated filesystem, custom command interpreter, and an extensible execution environment.

The goal is to explore how command-line interfaces, shell interpreters, filesystem operations, and system-level abstractions work internally.

> **Status:** Early Development — v0.1

---

## Overview

FilzaNix is designed to evolve into a Linux-inspired virtual computing environment with its own filesystem, command execution engine, scripting capabilities, and system utilities.

Rather than simply wrapping Windows commands, the project focuses on implementing its own command handling and virtual environment using Java.

## Features

### Currently Implemented

- Interactive command-line interface
- Custom terminal prompt
- Colored terminal output
- Startup and initialization sequence
- Basic command handling
- `help` — Display available commands
- `echo` — Print text to the terminal
- `exit` — Terminate the environment
- Initial virtual filesystem structure

### Planned Features

- Virtual filesystem and directory navigation
- File creation, reading, writing, and deletion
- Custom command parser
- Environment variables
- Command history
- File permissions simulation
- Command piping and output redirection
- Custom `.bar` scripting engine
- System logging
- Simulated process management

---

## Tech Stack

| Technology | Purpose |
|---|---|
| Java 21 | Core application |
| Java Standard Library | Input handling and filesystem operations |
| Windows Batch | Application launcher |
| Git & GitHub | Version control |

No external command interpreter or parser framework is currently required.

---

## Project Structure

```text
FilzaNix/
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── filzanix/
│                   │
│                   ├── Main.java
│                   │
│                   ├── shell/
│                   │   └── Shell.java
│                   │
│                   └── filesystem/
│                       └── VirtualFileSystem.java
│
├── env/
│   ├── home/
│   ├── etc/
│   ├── tmp/
│   └── root/
│
├── logs/
├── out/
│
├── FilzaNix.bat
├── .gitignore
└── README.md
```

### Directory Description

- `src/` — Java source code.
- `shell/` — Interactive terminal and command handling.
- `filesystem/` — Virtual filesystem implementation.
- `env/` — Root directory for the simulated environment.
- `logs/` — Application logs.
- `out/` — Compiled Java classes.
- `FilzaNix.bat` — Windows launcher.

---

## Requirements

- Java Development Kit (JDK) 21 or later
- Windows 10/11
- Windows Terminal or Command Prompt
- Git (optional)

Verify Java installation:

```bash
java -version
javac -version
```

---

## Installation & Execution

### 1. Clone the repository

```bash
git clone https://github.com/YOUR_USERNAME/FilzaNix.git
```

### 2. Navigate to the project

```bash
cd FilzaNix
```

### 3. Launch FilzaNix

Double-click:

```text
FilzaNix.bat
```

Alternatively, execute through PowerShell:

```powershell
.\FilzaNix.bat
```

The launcher compiles the Java source files and starts the application.

---

## Example Session

```text
==================================================
                 F I L Z A N I X
           Virtual Computing Environment
==================================================

[BOOT] Initializing environment...
[BOOT] Loading Java runtime...
[BOOT] Preparing system...

[ OK ] System initialized.

              FILZANIX v0.1

Runtime: Java 21

filzanix:~$ help

Available commands:
  help    - Show available commands
  echo    - Print text to the terminal
  exit    - Exit the environment

filzanix:~$ echo Hello World
Hello World

filzanix:~$ exit
Shutting down FilzaNix...
```

---

## Development Roadmap

| Version | Planned Milestone |
|---|---|
| v0.1 | Terminal initialization and input handling |
| v0.2 | Command execution architecture |
| v0.3 | Virtual filesystem |
| v0.4 | File and directory manipulation |
| v0.5 | Environment variables and logging |
| v0.6 | Advanced command parsing |
| v0.7 | Custom scripting engine |
| v1.0 | Stable virtual command-line environment |

---

## Design Philosophy

FilzaNix is being developed incrementally, with an emphasis on understanding the underlying mechanisms rather than relying on prebuilt abstractions.

The project explores:

- Object-oriented programming
- Command interpreters
- Filesystem abstraction
- Modular software architecture
- Input parsing
- Error handling
- System simulation

---

## Disclaimer

FilzaNix is an experimental Java-based virtual environment. It is not a Linux distribution, Linux kernel, or hardware-level virtual machine.

The project is intended for educational and software development purposes.

---

## Author

**Ashar**

Developed as an independent Java systems programming project.

