<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>DBC Logistica</title>
</head>
<body>
<div style="background-color: #f2f2f2; padding: 20px;">
    <div style="background-color: #dc1e1e;">
        <br>
        <h1 style="color: #f2f2f2;">DBC Logistica</h1>
        <br>
    </div>

    <h2>Olá, ${usuario.getNome()}!</h2>
    <p>Gostaríamos de lhe informar que uma nova viagem foi criada, seguem os dados da viagem: </p>
    <p>O local de partida é:  ${rota.getLocalPartida()}</p>
    <p>O local do destino é:  ${rota.getLocalDestino()}</p>
    <br>BOA VIAGEM!<br> <br>
    <br>Atenciosamente,<br>
    <br><b>${nome}</b>
</div>
</body>
</html>



<#--<html xmlns="http://www.w3.org/1999/xhtml">-->
<#--<head>-->
<#--    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />-->
<#--    <title>Java Mail</title>-->
<#--</head>-->

<#--<body>-->
<#--<table width="100%" border="0" cellspacing="0" cellpadding="0" style="background-color: #0053ff">-->
<#--    <tr>-->
<#--        <td align="center" valign="top" bgcolor="#838383"-->
<#--            style="background-color: #838383;"><br> <br>-->
<#--            <table width="600" border="0" cellspacing="0" cellpadding="0">-->
<#--                <tr>-->
<#--                    <td align="center" valign="top" bgcolor="#d3be6c"-->
<#--                        style="background-color: #ffffff; font-family: Arial, Helvetica, sans-serif; font-size: 13px; color: #000000; padding: 0px 15px 10px 15px;">-->

<#--                        <div style="font-size: 48px; color:blue;">-->
<#--                            <b>DBC LOGISTICA</b>-->
<#--                        </div>-->

<#--                        <div style="font-size: 24px; color: #555100;">-->
<#--                            <br>Olá, ${usuario.getNome()}</b> <br>-->
<#--                        </div>-->
<#--                        <div>-->
<#--                            <br>"Segue abaixo os dados necessários para que você possa fazer a rota" <br>-->
<#--                            <br>"O local de partida é: " ${rota.getLocalPartida()}<br> <br>-->
<#--                            <br>"O local do destino é: " ${rota.getLocalDestino()}<br> <br>-->
<#--                            <br>"BOA VIAGEM!<br> <br>-->
<#--                            <br>"Att,"<br>-->
<#--                            <br><b>${nome}</b>-->
<#--                            <br>-->
<#--                        </div>-->
<#--                    </td>-->
<#--                </tr>-->
<#--            </table> <br> <br></td>-->
<#--    </tr>-->
<#--</table>-->
<#--</body>-->
<#--</html>-->