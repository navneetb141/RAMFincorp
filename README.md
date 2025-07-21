# 🧪 SauceDemo Automation Framework

This is an end-to-end UI automation framework for [SauceDemo](https://www.saucedemo.com/) built with:

- ✅ Java
- ✅ Selenium WebDriver
- ✅ Cucumber (BDD)
- ✅ TestNG
- ✅ Allure Reporting
- ✅ FFmpeg (video recording)
- ✅ WebDriverManager

---

## 📁 Project Structure

```
SauceDemo/
├── src/
│   ├── main/
│   │   └── java/com/saucedemo/Test/
│   │       ├── pages/            # Page Object Model (POM)
│   │       ├── utils/            # BaseClass, config, helper utils
│   ├── test/
│   │   └── java/com/saucedemo/Test/
│   │       ├── hooks/            # Cucumber Hooks (MainHook)
│   │       ├── stepDefs/         # Step Definitions
│   │       ├── runners/          # TestNG Runners
│   │       ├── testData/         # Test data classes (if any)
│   │   └── resources/
│   │       └── features/         # .feature files (Gherkin)
├── allure-results/               # Allure raw result files
├── allure-report/                # Generated Allure HTML report
├── ScreenRecords/                # Scenario video recordings (.mp4)
├── Screenshots/                  # Screenshots for failed/passed tests
├── log/                          # Log files
├── pom.xml                       # Maven project file
├── testng.xml                    # TestNG runner config
└── README.md                     # Project documentation
```

---

## 🛠️ Technologies Used

| Tool             | Purpose                              |
|------------------|--------------------------------------|
| Java             | Programming Language                 |
| Maven            | Build & Dependency Management        |
| Selenium         | Web UI Automation                    |
| Cucumber         | BDD Framework                        |
| TestNG           | Test Execution                       |
| FFmpeg           | Record test execution (video)        |
| WebDriverManager | Auto-download browser drivers        |
| Allure           | Beautiful HTML Reporting             |

---

## ⚙️ Setup Instructions

### 🔧 Prerequisites

- JDK 11 or higher
- Maven 3.8+
- Google Chrome
- FFmpeg installed at `C:\RMAutomation_Selenium\ffmpeg` *(or update path in code)*

---

### 🚀 How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/saucedemo-automation.git
   cd saucedemo-automation
   ```

2. **Update config (optional)**  
   Edit `src/test/resources/config.properties` if needed:
   ```properties
   browser=chrome
   baseurl=https://www.saucedemo.com/
   id=standard_user
   password=secret_sauce
   ```

3. **Run tests with Maven**
   ```bash
   mvn clean test
   ```

4. **View Allure Report**
   ```bash
   allure serve allure-results
   ```

---

## 📸 Test Artifacts

- 🎥 **Videos** of each test run: stored in `ScreenRecords/`
- 🖼 **Screenshots** on failure/success: in `Screenshots/`
- 📊 **Allure Reports**: generated in `allure-report/`
- 📂 **Logs**: stored under `log/`

---

## 🔁 Rerun Failed Tests

Failed scenarios will be stored in:
```
target/failedScenarios.txt
```

Run them using `RerunTestRunner.java`.

---

## ✍️ Author

- **Navneet Biswas**
- GitHub: [your-github-username](https://github.com/your-github-username)

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
