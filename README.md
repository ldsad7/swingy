The file `en.subject.pdf` describes the task

Steps (run from the `tsimonis` directory):
- `brew install maven`
- `mvn clean package`
- `mysql -uUSER -p` > `enter password`
- `CREATE DATABASE IF NOT EXISTS swingy CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci`
- `SET GLOBAL time_zone = '+3:00';` (MSK timezone)

- `sh clean.sh`
- `sh run.sh`

Usage (from the directory `simulator/src`):
- `java com.tsimonis.avaj_launcher.Simulator pathToFile`
