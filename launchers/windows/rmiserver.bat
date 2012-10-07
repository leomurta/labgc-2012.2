@ECHO off
setlocal
::# Levantando o RMI
::
::Entrar com o caminho completo da pasta do jdk
::
set LABGC_JDK_PATH=C:\Program Files\Java\jdk1.7.0_03

::programa
set LABCG_OUT=%LABGC_JDK_PATH%\bin\rmiregistry
echo %LABCG_OUT%
echo Executando...
echo Ctrl+C para encerrar o RMIREGISTRY server

"%LABCG_OUT%"

endlocal

