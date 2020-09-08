@echo on

setlocal

set profileActive=dev

if "%1" NEQ "" set profileActive="%1"

mvn clean package -Dmaven.test.skip=true -P%profileActive% -f ../pom.xml