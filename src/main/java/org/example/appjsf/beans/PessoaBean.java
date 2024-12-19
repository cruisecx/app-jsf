package org.example.appjsf.beans;

import com.google.gson.Gson;
import org.example.appjsf.dao.DaoGeneric;
import org.example.appjsf.entities.Cidades;
import org.example.appjsf.entities.Estados;
import org.example.appjsf.entities.Pessoa;
import org.example.appjsf.repositories.IDaoPessoa;
import org.example.appjsf.repositories.IDaoPessoaImpl;
import org.example.appjsf.services.CidadeService;
import org.example.appjsf.services.MessageService;
import org.example.appjsf.services.PessoaService;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@ViewScoped
@Named
public class PessoaBean implements Serializable {

    private Pessoa pessoa = new Pessoa();
    private DaoGeneric<Pessoa> daoGeneric = new DaoGeneric<Pessoa>();
    private List<Pessoa> pessoas = new ArrayList<Pessoa>();

    private static final String URL_VIA_CEP = "https://viacep.com.br/ws/";

    private IDaoPessoa iDaoPessoa = new IDaoPessoaImpl();

    private List<SelectItem> estados;

    private List<SelectItem> cidades;

    private PessoaService service = new PessoaService();

    private Part arquivofoto;

    public String salvar() throws IOException {
//        /*Processar imagen*/
//        byte[] imagemByte = getByte(arquivofoto.getInputStream());
//        pessoa.setFotoIconBase64Original(imagemByte); // salva imagem original
//
//        // vamos transformar em miniatura
//        BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imagemByte));
//
//        // pega o tipo da imagem
//        int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
//
//        int largura = 200;
//        int altura = 200;
//
//        // bufered image para minatura
//        BufferedImage resizedImage = new BufferedImage(largura, altura, type);
//        Graphics2D g = resizedImage.createGraphics();
//        g.drawImage(bufferedImage, 0, 0, largura, altura, null);
//        g.dispose();
//
//        // escrever novamente a imagem
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        String extensao = arquivofoto.getContentType().split("\\/")[1];
//        ImageIO.write(resizedImage, extensao, baos);
//
//        String miniImagem = "data:" + arquivofoto.getContentType() + ";base64," +
//                DatatypeConverter.printBase64Binary(baos.toByteArray());

        /*Processar imagen*/
//        pessoa.setFotoIconBase64(miniImagem);
//        pessoa.setExtensao(extensao);

        pessoa = daoGeneric.merge(pessoa);
        carregarPessoas();
        mostrarMsg("Cadastrado com sucesso!");
        return "";
    }

    public void pesquisaCep(AjaxBehaviorEvent event) {
        try {
            System.out.println("Chamada do metodo de busca de cep : " + pessoa.getCep());
            URL url = new URL(URL_VIA_CEP + pessoa.getCep() + "/json/");
            URLConnection connection = url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            StringBuilder jsonCep = setarRetornoBufferEmString(bufferedReader, new StringBuilder());

            Pessoa gsonAux = new Gson().fromJson(jsonCep.toString(), Pessoa.class);

            System.out.println("===========================");
            System.out.println("WEB SERVICE BORA APRENDER");
            System.out.println(gsonAux.toString());
            System.out.println(jsonCep.toString());
            System.out.println("WEB SERVICE BORA APRENDER");

            pessoa.setCep(gsonAux.getCep());
            pessoa.setLogradouro(gsonAux.getLogradouro());
            pessoa.setComplemento(gsonAux.getComplemento());
            pessoa.setBairro(gsonAux.getBairro());
            pessoa.setLocalidade(gsonAux.getLocalidade());
            pessoa.setUf(gsonAux.getUf());
            pessoa.setIbge(gsonAux.getIbge());

        } catch (Exception e) {
            mostrarMsg("Ocorreu um erro");
        }

    }

    public StringBuilder setarRetornoBufferEmString(BufferedReader bufferedReader, StringBuilder jsonCep)
            throws IOException {
        String cep = "";
        while ((cep = bufferedReader.readLine()) != null) {
            jsonCep.append(cep);
        }
        System.out.println("Retorno : " + cep);

        return jsonCep;
    }

    public void registraLog() {
        System.out.println("método registraLog");
        /* Criar a rotina de gravação de log */
    }

    private void mostrarMsg(String msg) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage message = new FacesMessage(msg);
        context.addMessage(null, message);

    }

    public String UsuarioLogadoString(){
        // Obtém a instância atual do FacesContext
        FacesContext context = FacesContext.getCurrentInstance();

        // Obtém o ExternalContext para acessar a sessão
        ExternalContext externalContext = context.getExternalContext();

        // Recupera o objeto da sessão com a chave "usuarioLogado"
        Object usuarioLogado = externalContext.getSessionMap().get("usuarioLogado");

        if(usuarioLogado != null && usuarioLogado instanceof Pessoa){
            Pessoa pessoa = (Pessoa) usuarioLogado;
            return pessoa.getNome();
        }

        return null;
    }

    public String novo() {
        pessoa = new Pessoa();
        return "";
    }

    public String remove() {
        daoGeneric.deletePorId(pessoa);
        pessoa = new Pessoa();
        carregarPessoas();
        mostrarMsg("Removido com sucesso!");
        return "";
    }

    @PostConstruct
    public void carregarPessoas() {
        try {
            pessoas = daoGeneric.getListEntity(Pessoa.class);
        } catch (Exception e) {
            System.out.println("==============================");
            System.out.println(e.getMessage());
            System.out.println("==============================");
        }
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public DaoGeneric<Pessoa> getDaoGeneric() {
        return daoGeneric;
    }

    public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
        this.daoGeneric = daoGeneric;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public List<SelectItem> getEstados() {
        return service.getEstados();
    }

    public String deslogar() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");

        if (pessoaUser != null) {
            externalContext.getSessionMap().remove("usuarioLogado");

            HttpServletRequest httpServletRequest = (HttpServletRequest)
                    context.getCurrentInstance()
                            .getExternalContext()
                            .getRequest();

            httpServletRequest.getSession().invalidate();
            return "index.jsf";
        }

        MessageService.mostrarMessage("Ocorreu um erro");
        return "index.jsf";

    }

    public String logar() {

        Pessoa pessoaUser = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());

        if (pessoaUser != null) {// achou o usuário

            // adicionar o usuário na sessão usuarioLogado
            FacesContext context = FacesContext.getCurrentInstance();
            ExternalContext externalContext = context.getExternalContext();
            externalContext.getSessionMap().put("usuarioLogado", pessoaUser);

            return "primeirapagina.jsf";
        }

        return "index.jsf";
    }

    public boolean permiteAcesso(String acesso) {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");

        return pessoaUser.getPerfilUser().equals(acesso);
    }

    public void mudancaDeValor(ValueChangeEvent evento) {
        System.out.println("Valor antigo: " + evento.getOldValue());
        System.out.println("Valor Novo: " + evento.getNewValue());
    }

    public void mudancaDeValorSobrenome(ValueChangeEvent evento) {
        System.out.println("Valor antigo: " + evento.getOldValue());
        System.out.println("Valor Novo: " + evento.getNewValue());
    }

    public List<SelectItem> getCidades() {
        return cidades;
    }

    public void setCidades(List<SelectItem> cidades) {
        this.cidades = cidades;
    }

    public Part getArquivofoto() {
        return arquivofoto;
    }

    public void setArquivofoto(Part arquivofoto) {
        this.arquivofoto = arquivofoto;
    }

    public void carregaCidades(AjaxBehaviorEvent event) {
        Estados estado = (Estados) ((HtmlSelectOneMenu) event.getSource()).getValue();

        if (estado != null) {
            pessoa.setEstado(estado);

            @SuppressWarnings("unchecked")
            List<Cidades> cidades = CidadeService.getListaDeCidadesPeloEstadoId(estado.getId());
            List<SelectItem> selectItemsCidades = CidadeService.retornaSelectItemDeCidades(cidades);
            setCidades(selectItemsCidades);
        }
    }

    private byte[] getByte(InputStream is) throws IOException {
        int len;
        int size = 1024;
        byte[] buf = null;

        if (is instanceof ByteArrayInputStream) {
            size = is.available();
            buf = new byte[size];
            len = is.read(buf, 0, size);
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            buf = new byte[size];

            while ((len = is.read(buf, 0, size)) != -1) {
                bos.write(buf, 0, size);
            }

            buf = bos.toByteArray();
        }

        return buf;
    }

    @Override
    public String toString() {
        return "PessoaBean{" +
                "pessoa=" + pessoa +
                '}';
    }
}