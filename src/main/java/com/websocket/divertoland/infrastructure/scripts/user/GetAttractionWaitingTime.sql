SELECT
    COUNT(NomeUsuario) * CapacidadeBrinquedo
FROM
    Brinquedos
WHERE
    NomeAtracao = @nomeAtracao