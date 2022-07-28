package uz.jl.url;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uz.jl.url.dto.UrlCreateDTO;
import uz.jl.util.Utils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Controller
@RequiredArgsConstructor
public class UrlController {

    private final UrlService service;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView homePage() {
        ModelAndView modelAndView = new ModelAndView("index");
        List<UrlDomain> urlDomains = service.findAll();
        modelAndView.addObject("urls", urlDomains);
        return modelAndView;
    }

    @RequestMapping(value = "/gen/", method = RequestMethod.GET)
    public String urlGeneratePage(Model model) {
        model.addAttribute("dto", new UrlCreateDTO());
        return "gen/url-generate-page";
    }

    @RequestMapping(value = "/gen/", method = RequestMethod.POST)
    public String urlGenerate(@Valid @ModelAttribute("dto") UrlCreateDTO dto, BindingResult result) {
        if (result.hasErrors())
            return "gen/url-generate-page";
        service.generate(dto);
        return "redirect:/";
    }


    @RequestMapping(value = "/go/{shortenedUrl}", method = RequestMethod.GET)
    public void urlGeneratePage(@PathVariable String shortenedUrl, HttpServletResponse response) throws IOException {
        UrlDomain urlDomain = service.findByShortenedUrl(shortenedUrl);
        response.sendRedirect(urlDomain.getOriginalUrl());
    }

}

