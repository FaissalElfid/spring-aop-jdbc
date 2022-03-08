package ma.octo.aop.service.impl;

import ma.octo.aop.entity.Language;
import ma.octo.aop.repository.LanguageRepository;
import ma.octo.aop.service.LanguageService;
import ma.octo.aop.util.LRU;
import ma.octo.aop.util.Logger;
import ma.octo.aop.util.impl.LoggerImpl;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LanguageServiceImpl implements LanguageService {

  private final Logger logger;

  private final LanguageRepository languageRepository;

  public LanguageServiceImpl(final LanguageRepository languageRepository, Environment environment) {
    this.languageRepository = languageRepository;
    this.logger = new LoggerImpl(LanguageServiceImpl.class, environment);
  }

  @LRU
  @Override
  public Optional<Language> getLanguageById(final String id) {
    return languageRepository.findById(id);
  }

  @Override
  public Optional<Language> getLanguageByExtension(final String extension) {
    try {
      Thread.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return languageRepository.findByExtension(extension);
  }

  @Override
  public List<Language> findAllLanguages() {
    logger.debug("Getting a list of languages");
    return languageRepository.findAll();
  }

  @Override
  public void save(Language language) {
    logger.debug("Saving a language");
    System.out.println(languageRepository.save(language));
  }
  @Override
  public void init() {
    System.out.println("initializing the database");
    var java = new Language("java", "Java", "James Gosling", "java");
    var cpp = new Language("cpp", "C++", "Bjarne Stroustrup", "cpp");
    var cSharp = new Language("csharp", "C#", "Andres Hejlsberg", "cs");
    var perl = new Language("perl", "Perl", "Larry Wall", "pl");
    var haskell = new Language("haskel", "Haskell", "Simon Peyton", "hs");
    var lua = new Language("lua", "Lua", "Luiz Henrique", "lua");
    var python = new Language("python", "Python", "Guido van Rossum", "py");
    List<Language> LANGUAGES = List.of(java, cpp, cSharp, perl, haskell, lua, python);
    for (Language language:LANGUAGES) {
      languageRepository.save(language);
    }
  }
}
