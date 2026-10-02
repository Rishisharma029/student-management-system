# 🐙 GitHub Guide for Beginners

Welcome to GitHub! This is how software engineers save their work, track changes, and automatically test code.

## 🔄 Your Daily Workflow

1. **Stay on `main`**: Always ensure you are on the `main` branch.
2. **Open your Task**: Pick a task from `docs/TASK-ROADMAP.md`.
3. **Open the File**: Navigate to the exact file path.
4. **Edit Locally**: Write your Java code.
5. **Run Tests**: Check your work by running `.\mvnw.cmd clean test` in your terminal.

### 🚀 Push your work
When you're happy with your implementation, it's time to save it and send it to GitHub:

```bash
# 1. Stage your changes (tell Git what you want to save)
git add .

# 2. Take a snapshot of your work with a clear message
git commit -m "feat: implement JAVA-03"

# 3. Upload it to GitHub!
git push origin main
```

## 🤖 What is GitHub Actions?
GitHub Actions is an **automatic checker**. When you push code, GitHub spins up a virtual computer, runs your project's tests, and tells you whether they pass.

* **Green ✅**: The configured checks passed! (Your test worked).
* **Red ❌**: Something in the configured checks failed.

*(Note: Green does not mean the entire application is completely perfect forever, it just means the specific tests we wrote have successfully passed!)*

## 🚨 What to do when you get a Red ❌ Error?
Don't panic! Errors are normal. 

If GitHub says: `AttendanceServiceTest.java:175 failed`

**Do this:**
1. Open the file path (`AttendanceServiceTest.java`).
2. Go to the failing line (175).
3. Read the failing test name (What was it expecting?).
4. Read your task requirements again.
5. Fix the implementation in your code.
6. Run tests locally (`.\mvnw.cmd clean test`).
7. Once they pass locally, commit and push again!

**You do NOT need to understand the entire repository to fix one failing task.** Focus only on the exact file and method given to you.
