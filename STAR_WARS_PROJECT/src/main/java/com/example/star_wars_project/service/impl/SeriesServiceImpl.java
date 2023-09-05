package com.example.star_wars_project.service.impl;

import com.example.star_wars_project.model.binding.SeriesAddBindingModel;
import com.example.star_wars_project.model.entity.Picture;
import com.example.star_wars_project.model.entity.Series;
import com.example.star_wars_project.model.entity.enums.GenreNameEnum;
import com.example.star_wars_project.model.view.AllSerialsViewModel;
import com.example.star_wars_project.repository.GenreRepository;
import com.example.star_wars_project.repository.PictureRepository;
import com.example.star_wars_project.repository.SeriesRepository;
import com.example.star_wars_project.repository.UserRepository;
import com.example.star_wars_project.utils.CloudinaryImage;
import com.example.star_wars_project.service.CloudinaryService;
import com.example.star_wars_project.service.SeriesService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SeriesServiceImpl implements SeriesService {
    private final SeriesRepository seriesRepository;
    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryService cloudinaryService;
    private final PictureRepository pictureRepository;

    public SeriesServiceImpl(SeriesRepository seriesRepository, UserRepository userRepository, GenreRepository genreRepository, ModelMapper modelMapper, CloudinaryService cloudinaryService, PictureRepository pictureRepository) {
        this.seriesRepository = seriesRepository;
        this.userRepository = userRepository;
        this.genreRepository = genreRepository;
        this.modelMapper = modelMapper;
        this.cloudinaryService = cloudinaryService;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public List<AllSerialsViewModel> findAllSerialsOrderedByReleaseDate() {
        return seriesRepository
                .findAllSeriesByReleaseDate()
                .stream()
                .map(this::mapsSeriesToSeriesView)
                .collect(Collectors.toList());
    }

    @Override
    public List<AllSerialsViewModel> latestStarWarsSerials() {
        return seriesRepository
                .findNewestFourSeriesByReleaseDate()
                .stream()
                .map(this::mapsSeriesToSeriesView)
                .collect(Collectors.toList());
    }

    @Override
    public List<AllSerialsViewModel> findAllSeriesWithValueNullOrFalse() {
        return seriesRepository
                .findSeriesThatAreNotApproved()
                .stream()
                .map(this::mapsSeriesToSeriesView)
                .collect(Collectors.toList());
    }

    private AllSerialsViewModel mapsSeriesToSeriesView(Series series) {
        AllSerialsViewModel currentSerial = modelMapper.map(series, AllSerialsViewModel.class);
        Picture currentPicture = pictureRepository.findPictureBySeries_Id(series.getId());
        currentSerial.setPicture(currentPicture);
        return currentSerial;
    }

    @Override
    public Series findSerial(Long id) {
        return seriesRepository.findById(id).orElse(null);
    }

    @Override
    public void addSerial(SeriesAddBindingModel seriesAddBindingModel, String currentUserUsername) throws IOException {
        Series series = modelMapper.map(seriesAddBindingModel, Series.class);
        series.setAuthor(userRepository.findUserByUsername(currentUserUsername).orElse(null));

        series.setGenre(genreRepository.findByName(seriesAddBindingModel.getGenre()));

        seriesRepository.save(series);

        MultipartFile pictureMultipartFile = seriesAddBindingModel.getPicture();
        String pictureMultipartFileTitle = seriesAddBindingModel.getPictureTitle();
        final CloudinaryImage uploaded = cloudinaryService.upload(pictureMultipartFile);

        Picture picture = new Picture();
        picture.setPictureUrl(uploaded.getUrl());
        picture.setPublicId(uploaded.getPublicId());

        picture.setTitle(pictureMultipartFileTitle);
        picture.setAuthor(userRepository.findUserByUsername(currentUserUsername).orElse(null));
        picture.setSeries(seriesRepository.findSeriesByTitle(seriesAddBindingModel.getTitle()));
        pictureRepository.save(picture);
    }

    @Override
    public void approveSerialWithId(Long id) {
        Series series = seriesRepository.findSerialById(id);
        series.setApproved(true);
        seriesRepository.save(series);
    }

    @Override
    public void deleteSerialWithId(Long id) {
        List<Picture> allBySerialId = pictureRepository.findAllBySeries_Id(id);
        pictureRepository.deleteAll(allBySerialId);
        seriesRepository.deleteById(id);
    }

    @Override
    public void initSeries() {
        if (seriesRepository.count() > 0) {
            return;
        }

        Series series1 = new Series();
        series1.setApproved(null);
        series1.setDescription("In an era filled with danger, deception and intrigue, Cassian Andor will discover the difference he can make in the struggle against the tyrannical Galactic Empire. He embarks on a path that is destined to turn him into a rebel hero.");
        series1.setReleaseDate(LocalDate.of(2022, 9, 21));
        series1.setTitle("Andor");
        series1.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        series1.setGenre(genreRepository.findByName(GenreNameEnum.CRIME));


        Picture picture1 = new Picture();
        picture1.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680193752/Andor_l5emwa.webp");
        picture1.setPublicId("Andor_l5emwa");
        picture1.setTitle("Andor");
        picture1.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture1.setSeries(series1);

        seriesRepository.save(series1);
        pictureRepository.save(picture1);


        Series series2 = new Series();
        series2.setApproved(null);
        series2.setDescription("Jedi Master Obi-Wan Kenobi has to save young Leia after she is kidnapped, all the while being pursued by Imperial Inquisitors and his former Padawan, now known as Darth Vader.");
        series2.setReleaseDate(LocalDate.of(2022, 5, 27));
        series2.setTitle("Obi-Wan Kenobi");
        series2.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        series2.setGenre(genreRepository.findByName(GenreNameEnum.ADVENTURE));


        Picture picture2 = new Picture();
        picture2.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680193759/Obi-Wan_Kenobi_xr4qtd.webp");
        picture2.setPublicId("Obi-Wan_Kenobi_xr4qtd");
        picture2.setTitle("Obi-Wan Kenobi");
        picture2.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture2.setSeries(series2);

        seriesRepository.save(series2);
        pictureRepository.save(picture2);


        Series series3 = new Series();
        series3.setApproved(null);
        series3.setDescription("The legendary bounty hunter Boba Fett navigates the underworld of the galaxy with mercenary Fennec Shand when they return to the sands of Tatooine to stake their claim on the territory formerly ruled by the deceased crime lord Jabba the Hutt.");
        series3.setReleaseDate(LocalDate.of(2021, 12, 29));
        series3.setTitle("The Book of Boba Fett");
        series3.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        series3.setGenre(genreRepository.findByName(GenreNameEnum.CRIME));


        Picture picture3 = new Picture();
        picture3.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680193768/The_Book_of_Boba_Fett_ep6pbn.webp");
        picture3.setPublicId("The_Book_of_Boba_Fett_ep6pbn");
        picture3.setTitle("The Book of Boba Fett");
        picture3.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture3.setSeries(series3);

        seriesRepository.save(series3);
        pictureRepository.save(picture3);


        Series series4 = new Series();
        series4.setApproved(null);
        series4.setDescription("The Star Wars saga continues from Executive Producer George Lucas and Lucasfilm Animation! With cutting-edge, feature-film quality computer animation, classic characters, astounding action, and the timeless battle between good and evil, Star Wars: The Clone Wars expands the Star Wars story with all new adventures set in a galaxy far, far away.");
        series4.setReleaseDate(LocalDate.of(2008, 8, 15));
        series4.setTitle("The Clone Wars");
        series4.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        series4.setGenre(genreRepository.findByName(GenreNameEnum.ANIMATION));


        Picture picture4 = new Picture();
        picture4.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1680193774/The_Clone_Wars_iiguxr.jpg");
        picture4.setPublicId("The_Clone_Wars_iiguxr");
        picture4.setTitle("The Clone Wars");
        picture4.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture4.setSeries(series4);

        seriesRepository.save(series4);
        pictureRepository.save(picture4);


        Series series5 = new Series();
        series5.setApproved(null);
        series5.setDescription("It is a dark time in the galaxy as the Galactic Empire continues to tighten its grip on the people through oppression and fear, compelling a few brave individuals to band together in resistance. The motley crew of the starship Ghost stands up for those who cannot fight for themselves, providing the spark to ignite a rebellion.");
        series5.setReleaseDate(LocalDate.of(2017, 10, 16));
        series5.setTitle("Star Wars Rebels");
        series5.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        series5.setGenre(genreRepository.findByName(GenreNameEnum.ANIMATION));


        Picture picture5 = new Picture();
        picture5.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1691915057/d4mugdanohtamwc8acd1.webp");
        picture5.setPublicId("d4mugdanohtamwc8acd1");
        picture5.setTitle("Star Wars Rebels");
        picture5.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture5.setSeries(series5);

        seriesRepository.save(series5);
        pictureRepository.save(picture5);


        Series series6 = new Series();
        series6.setApproved(null);
        series6.setDescription("A six-episode event featuring parables built around Jedi from the prequel era. Journey into the lives of two distinctly different Jedi -- Ahsoka Tano and Count Dooku. Each will be put to the test as they make choices that will define their destinies.  ");
        series6.setReleaseDate(LocalDate.of(2022, 10, 26));
        series6.setTitle("Tales of the Jedi");
        series6.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        series6.setGenre(genreRepository.findByName(GenreNameEnum.ANIMATION));


        Picture picture6 = new Picture();
        picture6.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1691916145/hkhxomm3ksyp9euona8m.webp");
        picture6.setPublicId("hkhxomm3ksyp9euona8m");
        picture6.setTitle("Tales of the Jedi");
        picture6.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture6.setSeries(series6);

        seriesRepository.save(series6);
        pictureRepository.save(picture6);


        Series series7 = new Series();
        series7.setApproved(null);
        series7.setDescription("The Bad Batch follows the elite and experimental clones of the Bad Batch (first introduced in “The Clone Wars”) as they find their way in a rapidly changing galaxy in the immediate aftermath of the Clone War. Members of Bad Batch—a unique squad of clones who vary genetically from their brothers in the Clone Army—each possess a singular exceptional skill that makes them extraordinarily effective soldiers and a formidable crew.  ");
        series7.setReleaseDate(LocalDate.of(2021, 5, 4));
        series7.setTitle("Star Wars: The Bad Batch");
        series7.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        series7.setGenre(genreRepository.findByName(GenreNameEnum.ANIMATION));


        Picture picture7 = new Picture();
        picture7.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1691915262/yeudbahcv9vc7tidazdq.webp");
        picture7.setPublicId("yeudbahcv9vc7tidazdq");
        picture7.setTitle("Star Wars: The Bad Batch");
        picture7.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture7.setSeries(series7);

        seriesRepository.save(series7);
        pictureRepository.save(picture7);


        Series series8 = new Series();
        series8.setApproved(null);
        series8.setDescription("After the fall of the Empire, a lone Mandalorian makes his way through the lawless galaxy with his foundling, Grogu.  ");
        series8.setReleaseDate(LocalDate.of(2023, 3, 1));
        series8.setTitle("The Mandalorian");
        series8.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        series8.setGenre(genreRepository.findByName(GenreNameEnum.ACTION));


        Picture picture8 = new Picture();
        picture8.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1691915565/xvzqeutuefx50tqtewic.webp");
        picture8.setPublicId("xvzqeutuefx50tqtewic");
        picture8.setTitle("The Mandalorian");
        picture8.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture8.setSeries(series8);

        seriesRepository.save(series8);
        pictureRepository.save(picture8);


        Series series9 = new Series();
        series9.setApproved(null);
        series9.setDescription("Set after the fall of the Empire, \"Ahsoka\" follows the former Jedi knight Ahsoka Tano as she investigates an emerging threat to a vulnerable galaxy.  ");
        series9.setReleaseDate(LocalDate.of(2023, 8, 22));
        series9.setTitle("Ahsoka");
        series9.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        series9.setGenre(genreRepository.findByName(GenreNameEnum.ADVENTURE));


        Picture picture9 = new Picture();
        picture9.setPictureUrl("https://res.cloudinary.com/dedh1hh8k/image/upload/v1693902908/ipefsschdggliykiakpu.jpg");
        picture9.setPublicId("ipefsschdggliykiakpu");
        picture9.setTitle("Ahsoka");
        picture9.setAuthor(userRepository.findUserByUsername("Admin").orElse(null));
        picture9.setSeries(series9);

        seriesRepository.save(series9);
        pictureRepository.save(picture9);
    }
}
