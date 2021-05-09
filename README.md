The file `en.subject.pdf` describes the task

Steps (run from the `tsimonis` directory):
- `brew install maven`
- `mvn clean package`
- `mysql -uUSER -p` > `enter password`
- `SET GLOBAL time_zone = '+3:00';` (MSK timezone)

- `sh clean.sh`
- `sh run.sh`

Usage (from the directory `simulator/src`):
- `java com.tsimonis.avaj_launcher.Simulator pathToFile`
