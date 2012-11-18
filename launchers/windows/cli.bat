@ECHO off
setlocal
:: Levantando o CLI do controle de versao

:::: ENTRAR COM O CAMINHO CORRETO RELATIVO AO USUARIO
set LABCG_PATH="../../target/labgc-2012.2.jar"
set LABCG_RMI_CODEBASE="../../target/classes/"
set LABCG_RMI_HOSTNAME=localhost
set LABCG_POLICITY="../../target/classes/evcs.policy"
set LABCG_REPOSITORY="../../repositorio/"
::
::

::programa
for %%i in (%LABCG_PATH%) do (set LABCG_PATH=%%~si)
for %%i in (%LABCG_RMI_CODEBASE%) do (set LABCG_RMI_CODEBASE=%%~si)
for %%i in (%LABCG_POLICITY%) do (set LABCG_POLICITY=%%~si)
for %%i in (%LABCG_REPOSITORY%) do (set LABCG_REPOSITORY=%%~si)

set LABCG_OUT="java -cp %LABCG_PATH% -Dbr.uff.ic.labgc.invocation=%~sdp0 -Dbr.uff.ic.labgc.repository=%LABCG_REPOSITORY% -Djava.rmi.server.codebase=file:\%LABCG_RMI_CODEBASE% -Djava.rmi.server.hostname=%LABCG_RMI_HOSTNAME% -Djava.security.policy=%LABCG_POLICITY% br.uff.ic.labgc.AppCLI %*"
echo %LABCG_OUT%

echo Executando...
echo .


"%LABCG_OUT%"

endlocal