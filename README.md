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
