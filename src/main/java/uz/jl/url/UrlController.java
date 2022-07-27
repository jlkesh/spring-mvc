package uz.jl.url;

import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.jl.url.dto.UrlCreateDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class UrlController {


    private final UrlService service;

    public UrlController(UrlService service) {
        this.service = service;
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<UrlDomain> urlDomains = service.findAll();
        modelAndView.addObject("urls", urlDomains);
        return modelAndView;
    }

    @RequestMapping(value = "/gen/", method = RequestMethod.GET)
    public String urlGeneratePage() {
        return "gen/url-generate-page";
    }

    @RequestMapping(value = "/gen/", method = RequestMethod.POST)
    public String urlGenerate(@NonNull @ModelAttribute UrlCreateDTO dto) {
        service.generate(dto);
        return "redirect:/";
    }


    @RequestMapping(value = "/go/{shortenedUrl}", method = RequestMethod.GET)
    public void urlGeneratePage(@PathVariable String shortenedUrl, HttpServletResponse response) {
        UrlDomain urlDomain = service.findByShortenedUrl(shortenedUrl);
        try {
            TimeUnit.SECONDS.sleep(1);
            response.sendRedirect(urlDomain.getOriginalUrl());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public String urlNotFoundException(RuntimeException e) {
        return e.getMessage();
    }


}
