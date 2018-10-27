//package com.emu.apps.qcm.services.repositories.custom;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.support.JpaEntityInformation;
//import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
//import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
//import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
//import org.springframework.data.repository.core.RepositoryInformation;
//import org.springframework.data.repository.core.RepositoryMetadata;
//import org.springframework.data.repository.core.support.RepositoryFactorySupport;
//
//import javax.persistence.EntityManager;
//import java.io.Serializable;
//
//public class CustomRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable> extends JpaRepositoryFactoryBean<R, T, I> {
//
//    private RepositoryConfiguration repositoryConfiguration;
//
//    public CustomRepositoryFactoryBean(Class<? extends R> repositoryInterface, RepositoryConfiguration repositoryConfiguration) {
//        super(repositoryInterface);
//        this.repositoryConfiguration = repositoryConfiguration;
//    }
//
//    @Override
//    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
//        return new CustomRepositoryFactory(entityManager, repositoryConfiguration);
//    }
//
//    private static class CustomRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {
//
//        private RepositoryConfiguration repositoryConfiguration;
//
//        CustomRepositoryFactory(EntityManager entityManager, RepositoryConfiguration repositoryConfiguration) {
//            super(entityManager);
//            this.repositoryConfiguration = repositoryConfiguration;
//        }
//
////        @Override
////        protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
////            return new CustomRepositoryFactory(entityManager, repositoryConfiguration);
////        }
//
//        @Override
//        @SuppressWarnings("unchecked")
//        protected SimpleJpaRepository<?, ?> getTargetRepository(RepositoryInformation information,
//                                                                EntityManager entityManager) {
//
//            JpaEntityInformation<T, ?> entityInformation =
//                    (JpaEntityInformation<T, ?>) getEntityInformation(information.getDomainType());
//
//            return new SimpleJpaBulkRepositoryImpl<T, I>(
//                    entityInformation,
//                    entityManager, repositoryConfiguration);
//        }
//
//        @Override
//        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
//            return SimpleJpaBulkRepositoryImpl.class;
//        }
//    }
//}