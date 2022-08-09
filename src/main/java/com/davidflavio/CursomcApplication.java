package com.davidflavio;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.davidflavio.domain.Categoria;
import com.davidflavio.domain.Cidade;
import com.davidflavio.domain.Cliente;
import com.davidflavio.domain.Endereco;
import com.davidflavio.domain.Estado;
import com.davidflavio.domain.ItemPedido;
import com.davidflavio.domain.Pagamento;
import com.davidflavio.domain.PagamentoComBoleto;
import com.davidflavio.domain.PagamentoComCartao;
import com.davidflavio.domain.Pedido;
import com.davidflavio.domain.Produto;
import com.davidflavio.domain.enums.EstadoPagamento;
import com.davidflavio.domain.enums.TipoCliente;
import com.davidflavio.repositories.CategoriaRepository;
import com.davidflavio.repositories.CidadeRepository;
import com.davidflavio.repositories.ClienteRepository;
import com.davidflavio.repositories.EnderecoRepository;
import com.davidflavio.repositories.EstadoRepository;
import com.davidflavio.repositories.ItemPedidoRepository;
import com.davidflavio.repositories.PagamentoRepository;
import com.davidflavio.repositories.PedidoRepository;
import com.davidflavio.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRespository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private ClienteRepository clienteRespoitory;
	@Autowired
	private EnderecoRepository enderecoRepository; 
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null,"Computador", 2000.00);
		Produto p2 = new Produto(null,"Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		Estado est1 = new Estado(null,"Minas Gerais");
		Estado est2 = new Estado(null,"São Paulo");
		Estado est3 = new Estado(null,"Ceará");
		
		Cidade c1 = new Cidade(null,"Uberlandia", est1);
		Cidade c2 = new Cidade(null,"São Paulo", est2);
		Cidade c3 = new Cidade(null,"Campinas",est2);
		Cidade c4 = new Cidade(null,"Fortaleza", est3);
		Cidade c5 = new Cidade(null,"Pacatuba",est3);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		est3.getCidades().addAll(Arrays.asList(c4,c5));
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "marias@gmail.com", "12312312312", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("2222-33333","3333-4444"));
		
		Endereco e1 = new Endereco(null, "Rua das Flores", "300", "ap 303", "Jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null,"Avenida Matos","105", "Sala 800","Centro","38777012",cli1,c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2021 10:32"),cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2021 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO,ped1,6);
		ped1.setPagamento(pagto1);
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2021 10:00"),null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2 ,80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRespository.saveAll(Arrays.asList(p1,p2,p3));
		estadoRepository.saveAll(Arrays.asList(est1,est2,est3));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3,c4,c5));
		clienteRespoitory.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
	}
	

}
