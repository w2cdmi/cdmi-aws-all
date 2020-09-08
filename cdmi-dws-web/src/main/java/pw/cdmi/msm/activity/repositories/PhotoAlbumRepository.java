package pw.cdmi.msm.activity.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import pw.cdmi.msm.activity.model.entities.PhotoAlbum;

public interface PhotoAlbumRepository extends PagingAndSortingRepository<PhotoAlbum, String>,JpaRepository<PhotoAlbum, String>{
	
}
