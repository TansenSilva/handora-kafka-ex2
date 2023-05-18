package br.com.zup.edu.marketplace.promocao;

import br.com.zup.edu.marketplace.produto.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promocoes")
public class PromocaoController {

    Logger logger = LoggerFactory.getLogger(PromocaoController.class);

    @Autowired
    private PromocaoRepository promocaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private NovaPromocaoProducer novaPromocaoProducer;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PromocaoResponse incluir(@RequestBody PromocaoRequest promocaoRequest){

        var promocao = promocaoRequest.toModel(produtoRepository);

        promocaoRepository.save(promocao);

        logger.info(" Promoção de id {} cadastrada com sucesso ", promocao.getId());

        novaPromocaoProducer.enviar(promocao);

        return PromocaoResponse.of(promocao);
    }
}
