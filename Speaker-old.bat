@echo off
@mode con cols=55 lines=10
title Talk
color A
:st
cls
cd "%userprofile%\desk*"
if exist Talk_.vbs del Talk_.vbs
copy NUL Talk_.vbs
cls
ping localhost -n 2 > nul
echo strText = ("%~1")> "Talk_.vbs"
echo Set objvoice = CreateObject("SAPI.SpVoice")>> "Talk_.vbs"
echo ObjVoice.Speak strText>> "Talk_.vbs"
cls

cd "%userprofile%\desk*"
start Talk_.vbs
ping localhost -n 3 > nul
del Talk_.vbs
cd..
cls