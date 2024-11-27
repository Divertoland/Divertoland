SELECT
    NomeUsuario
FROM
    Usuario
WHERE
    StatusUser = 'Queue' && BrinquedoAtual = @brinquedoAtual