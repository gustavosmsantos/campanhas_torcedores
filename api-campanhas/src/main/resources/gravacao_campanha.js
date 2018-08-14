function gravacaoCampanha(params) {

    var db = require("@arangodb").db;
    const aql = require('@arangodb').aql;

    const campanhas = db._collection('campanhas');
    const campanhas_times = db._collection('campanhas_times');

    var campanhaSalva = campanhas.save(params.campanha);
    var campanha = Object.assign(params.campanha, campanhaSalva);
    campanhas_times.save({ _from: campanha._id, _to: params.timeId });

    var campanhasAfetadas = db._query(aql `
        FOR c IN ${campanhas}
            FILTER c.\`inicio\` <= ${campanha.inicio}
            AND c.\`fim\` >= ${campanha.fim}
            AND c.\`_id\` != ${campanha._id}
        RETURN c
    `).toArray();

    for (var i = 0; i < campanhasAfetadas.length; i++) {
        var campanhaAfetada = campanhasAfetadas[i];

        var fim = new Date(campanhaAfetada.fim);

        var novoFim = new Date(fim);
        var found = [""];
        var novoFimStr = `${novoFim.getFullYear()}-${novoFim.getMonth() + 1}-${novoFim.getDate()}`;
        while (found && found.length) {
            novoFim.setDate(novoFim.getDate() + 1);
            novoFimStr = `${novoFim.getFullYear()}-${novoFim.getMonth() + 1}-${novoFim.getDate()}`;
            found = db._query(aql `
                FOR c IN ${campanhas}
                    FILTER c.\`fim\` == ${novoFim}
                RETURN c._id
            `).toArray();
        }

        db._query(aql `
            FOR c IN ${campanhas} FILTER c.\`_id\` == ${campanhaAfetada._id} UPDATE c WITH { \`fim\`: ${novoFimStr} } IN ${campanhas}
        `);
    }

    return { campanhaSalva: campanha, campanhasAfetadas: campanhasAfetadas };

}