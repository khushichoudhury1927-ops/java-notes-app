# Java Notes App 📝

**ElevateLabs Java Developer Internship — Task 4**

---

## What I Built

A console-based notes manager where you can add, view, delete, and search notes — and everything gets saved to a file so your notes are still there the next time you run the app.

This was my first time working with Java File I/O, and the main goal was to understand how to actually persist data instead of losing it when the program closes.

---

## Features

- **Add a note** — saves it to `notes.txt` with a timestamp
- **See all notes** — reads and lists everything from the file
- **Delete a note** — pick one by number and it's gone
- **Search notes** — type a keyword and find matching notes
- **Clear everything** — wipe the file and start fresh

---

## How to Run

```bash
javac NotesApp.java
java NotesApp
```

The app will greet you by name and show a simple menu. `notes.txt` gets created automatically on your first note.

---

## Sample Session

```
Hey! What's your name?
> Khushi

Nice to meet you, Khushi! Welcome to your Notes App.
All your notes are saved to a file, so they'll be here next time too.

What would you like to do?
  1. Add a note
  2. See all notes
  3. Delete a note
  4. Search notes
  5. Clear everything
  6. Exit

Your choice: 1

What's on your mind?
> Buy groceries tomorrow

Got it! Saved on 05 Jun 2026, 10:14 PM.
```

---

## What I Learned

**FileWriter** — writes text to a file. The second argument controls the mode:
- `true` → append mode (adds to the end, keeps existing notes safe)
- `false` → overwrite mode (replaces everything, used when deleting or clearing)

**FileReader + BufferedReader** — reads the file back. `FileReader` does the actual file access, and `BufferedReader` wraps it so we can read one full line at a time with `readLine()` instead of character by character.

**try-with-resources** — a cleaner way to handle files in Java. Whatever you open inside `try(...)` gets closed automatically when you're done, even if something crashes midway.

```java
try (FileWriter fw = new FileWriter(FILE_NAME, true);
     BufferedWriter bw = new BufferedWriter(fw)) {
    bw.write(note);
    bw.newLine();
}
// file closes on its own here — no finally block needed
```

**Checked vs unchecked exceptions** — `IOException` is checked, meaning Java forces you to handle it at compile time. `NumberFormatException` is unchecked, meaning it shows up at runtime and you handle it if you want to.

---

## Interview Questions — My Answers

**1. FileReader vs BufferedReader?**
`FileReader` reads one character at a time straight from the file. `BufferedReader` wraps it and reads in bigger chunks, which is faster. It also gives you `readLine()` which is really handy.

**2. What is try-with-resources?**
A Java 7 feature where you declare your file (or any resource) inside `try(...)` and Java closes it for you automatically — even if an exception happens. Cleaner than writing a `finally` block every time.

**3. How to handle IOException?**
Wrap your file code in a `try-catch (IOException e)` block and decide what to do when something goes wrong — show an error message, log it, or try again.

**4. Checked vs unchecked exceptions?**
Checked exceptions (like `IOException`) must be handled at compile time — Java won't let you ignore them. Unchecked exceptions (like `NumberFormatException`) only show up at runtime and handling them is optional.

**5. How does file writing work in Java?**
You open a `FileWriter` to connect to the file, optionally wrap it in `BufferedWriter` for speed, write your content, and close it. Closing flushes any buffered data to disk.

**6. Append vs overwrite mode?**
`new FileWriter("notes.txt", true)` adds to the end of the file. `new FileWriter("notes.txt", false)` (or just without the second argument) starts fresh and replaces everything.

**7. What is exception propagation?**
If a method doesn't catch an exception, it passes it up to whoever called that method. If nobody catches it all the way up, the JVM crashes the program and prints the stack trace.

**8. How to log exceptions?**
`e.getMessage()` gives you a short description. `e.printStackTrace()` prints the full chain of method calls that led to the error. For production apps you'd use a logging library like Log4j.

**9. What is a stack trace?**
The list of method calls that were running when the error happened. It tells you exactly where things went wrong and how the program got there — super useful for debugging.

**10. When to use finally block?**
When you need something to run no matter what — success or failure. Most commonly used for cleanup like closing files or database connections. With try-with-resources, you rarely need `finally` for that anymore.

---

## Files

```
java-notes-app/
├── NotesApp.java   ← the whole app lives here
├── notes.txt       ← created automatically when you add your first note
└── README.md
```
