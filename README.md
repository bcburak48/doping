# Öğrenci Sınav Yönetim Sistemi (Student Exam Management System)

## Proje Özeti

Bu projede, öğrencilerin sınavlarına katılabileceği ve sınav sonuçlarını görüntüleyebileceği bir hizmet geliştirilmiştir. Veriler, H2 in-memory veritabanında saklanmaktadır. Proje, Spring Boot kullanılarak geliştirilmiş ve RESTful servisler şeklinde sunulmuştur.

## Özellikler

- Öğrenci Kaydı: Ad, soyad, öğrenci numarası gibi öğrenci bilgileri kaydedilebilir.
- Sınav Kaydı: Sınav adı, sınav soruları ve cevapları gibi sınav bilgileri tutulabilir.
- Öğrenci Katılımı: Öğrenciler bir veya birden fazla sınavı çözebilir.
- Sınav Katılımı: Bir sınav, birden fazla öğrenci tarafından çözülebilir.
- Performans Görüntüleme: Öğrenciler sınav performanslarını görüntüleyebilirler.
- Validasyonlar: Servislerde gerekli validasyonlar eklenmiştir.
- Cache Kullanımı: Cache kullanılarak servis istek süreleri optimize edilmiştir.

## API Dökümantasyonu

Projenin API dökümantasyonu Swagger ile sağlanmaktadır. API'ye erişim için aşağıdaki linki kullanabilirsiniz:

[Swagger UI](http://localhost:8080/swagger-ui/index.html#/)

## Proje Çalıştırma

Proje Java 21 sürümü ile oluşturulmuştur. Proje kodlarına aşağıdaki GitHub bağlantısından erişebilirsiniz:

[Github Repository](https://github.com/bcburak48/doping)

Projeyi yerel bir ortamda çalıştırmak için aşağıdaki adımları izleyebilirsiniz:

1. Proje kodlarını GitHub'dan indirin veya klonlayın.
2. Proje dizinine gidin ve terminal veya komut istemcisini açın.
3. Aşağıdaki komutu çalıştırarak projeyi başlatın:

./mvnw spring-boot:run

4. Proje başladığında, tarayıcınızı veya API test aracınızı kullanarak Swagger API dokümantasyonuna erişebilirsiniz: [Swagger UI](http://localhost:8080/swagger-ui/index.html#/)

## Birim Testler

Proje için birim testler eklenmiştir ve bu testler proje kodları içinde bulunmaktadır. Birim testler, projenin doğru ve güvenilir bir şekilde çalıştığını doğrulamak için kullanılabilir.
