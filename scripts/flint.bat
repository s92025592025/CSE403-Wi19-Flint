@ECHO OFF
SET CURRENT_DIR=%~dp0
SET FILE="flint-0.1.3.jar"
FOR /r %CURRENT_DIR% %%a IN (*) DO IF "%%~nxa"==%FILE% SET FILE_PATH=%%~dpnxa
IF EXIST %FILE_PATH% (
  java -jar %FILE_PATH% %*
) ELSE (
  ECHO Please make sure the flint-0.1.3.jar file is in the same directory as the flint.bat script!
)
