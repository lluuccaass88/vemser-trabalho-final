<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>DBC Logistical</title>
</head>

<body>
<div style="background-color: #f2f2f2; padding: 20px;">
    <div style="background-color: #dc1e1e; padding: 40px;">
        <br>
        <h1 style="color: #f2f2f2;"> DBC Logistica</h1>
        <br>
    </div>

    <h2>Olá, ${usuario.getNome()}, seja bem vindo!</h2>
    <p>É com grande prazer que lhe damos as boas-vindas à equipe da ${nome}!  </p>
    <p>Nosso principal objetivo é fornecer aos nossos clientes os melhores serviços personalizados e soluções em logística.</p>
    <p>E isto não seria possivel sem a contribuição dos nossos motoristas, que são peça chave neste processo!</p>
    <p>Seguem seus dados cadastrados:</p>
    <p>Id:  ${usuario.getId()}</p>
    <p>Email:  ${usuario.getEmail()}</p>
    <p>Qualquer duvida é só contatar o suporte pelo email -> " ${usuario.getEmail()}</p>
    <p>Atenciosamente,</p>
    <p><b>${nome}</p>
</div>

</body>
</html>
<#-- <body>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="center" valign="top" bgcolor="#838383"
            style="background-color: #838383;"><br> <br>
            <table width="600" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td align="center" valign="top" bgcolor="#d3be6c"
                        style="background-color: #d3be6c; font-family: Arial, Helvetica, sans-serif; font-size: 13px; color: #000000; padding: 0px 15px 10px 15px;">

                        <div style="font-size: 48px; color:blue;">
                            <b>DBC LOGISTICA</b>
                        </div>

                        <div style="font-size: 24px; color: #555100;">
                            <br>Olá, ${usuario.getNome()}</b> <br>
                        </div>
                        <div>
                            <br>"Estamos felizes em ter você em nosso sistema :)" <br>
                            <br>"Seu cadastro foi realizado com sucesso e o seu ID é: " ${usuario.getId()}<br> <br>
                            <br>"Qualquer duvida é só contatar o suporte pelo email -> " ${usuario.getEmail()}<br> <br>
                            <br>"Att,"<br>
                            <br><b>${nome}</b>
                            <br>
                        </div>
                    </td>
                </tr>
            </table> <br> <br></td>
    </tr>
</table>
</body>
</html> -->