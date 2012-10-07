@ECHO off
::# Levantando o RMI
::
::Entrar com o caminho completo da pasta do jdk no aruqivo jdk_path.txt
::

set _LABGC_FILE=jdk~path.txt
set _LABGC_RMI_BIN=\bin\rmiregistry.exe

call :TXTCHECK %_LABGC_FILE%
EXIT /B


REM =======================================================================================
REM =   Check txt location
REM =======================================================================================
:TXTCHECK
if exist "%~1" (
	for /F "delims=" %%a in (%~1) do (call :RMICHECK "%%a")
) else (
    call :ENTRYJDK
)
EXIT /B


REM =======================================================================================
REM =   Check RMISEVER location
REM =======================================================================================
:RMICHECK
if exist %~1 (
	call :EXECUTE "%~1"
) else (
	call :ENTRYJDK
)
EXIT /B


REM =======================================================================================
REM =   Execute RMISERVER
REM =======================================================================================
:EXECUTE
echo "%~s1"
echo Executando...
echo Ctrl+C para encerrar o RMIREGISTRY server
"%~s1"
EXIT


REM =======================================================================================
REM =   Entry JDK Location
REM =======================================================================================
:ENTRYJDK
echo .
echo Entre com o caminho do JDK" 
set /p LABCG_JDK=JDK:
call :WRITEFILE "%LABCG_JDK%%_LABGC_RMI_BIN%"
EXIT /B


REM =======================================================================================
REM =   Write text file
REM =======================================================================================
:WRITEFILE
if exist "%~S1" (
	if exist "%_LABGC_FILE%" (del %_LABGC_FILE%)
    ECHO %~S1 >> %_LABGC_FILE%
	echo arquivo criado!
) else (
    echo Nao foi possivel encontrar o arquivo no local especificado! Rode o comando novamente!
)
EXIT /B
