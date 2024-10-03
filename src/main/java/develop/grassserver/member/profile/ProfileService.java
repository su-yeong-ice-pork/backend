package develop.grassserver.member.profile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import develop.grassserver.member.Member;
import develop.grassserver.member.MemberRepository;
import develop.grassserver.member.profile.banner.Banner;
import develop.grassserver.member.profile.banner.BannerRepository;
import develop.grassserver.member.profile.exeption.ImageUploadFailedException;
import develop.grassserver.member.profile.image.Image;
import develop.grassserver.member.profile.image.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private static final String PROFILE_BANNER_SAVE_PATH_PREFIX = "profile/banners/";
    private static final String PROFILE_IMAGE_SAVE_PATH_PREFIX = "profile/images/";

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3Client;
    private final ImageRepository imageRepository;
    private final MemberRepository memberRepository;
    private final BannerRepository bannerRepository;

    @Transactional
    public void saveBannerImage(MultipartFile image, Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(EntityNotFoundException::new);

        Banner banner = getUploadedBanner(image, findMember);
        bannerRepository.save(banner);
    }

    private Banner getUploadedBanner(MultipartFile image, Member member) {
        File file = tryConvertFile(image);
        String url = uploadBanner(file);
        return Banner.builder()
                .url(url)
                .member(member)
                .build();
    }

    private String uploadBanner(File file) {
        String fileName = PROFILE_BANNER_SAVE_PATH_PREFIX + file.getName();
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    @Transactional
    public void saveProfileImage(MultipartFile image, Long memberId) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(EntityNotFoundException::new);

        Image uploadImage = getUploadedProfileImage(image, findMember);
        imageRepository.save(uploadImage);
    }

    private Image getUploadedProfileImage(MultipartFile image, Member member) {
        File file = tryConvertFile(image);
        String url = uploadProfileImage(file);
        return Image.builder()
                .url(url)
                .member(member)
                .build();
    }

    private String uploadProfileImage(File file) {
        String fileName = PROFILE_IMAGE_SAVE_PATH_PREFIX + file.getName();
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, file));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private File tryConvertFile(MultipartFile image) {
        try {
            return convertFile(image).orElseThrow(IllegalArgumentException::new);
        } catch (IOException e) {
            log.error("Do not converted multipart file to image.");
            throw new ImageUploadFailedException();
        }
    }

    private Optional<File> convertFile(MultipartFile image) throws IOException {
        File file = new File(Objects.requireNonNull(image.getOriginalFilename()));
        if (file.createNewFile()) {
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                outputStream.write(image.getBytes());
            }
            return Optional.of(file);
        }
        return Optional.empty();
    }
}
