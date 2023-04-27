BACKUP_PATH=./volume_backup &&
BACKUP_DOCKER_PATH=volume_backup &&
VOLUME_NAME=home-library_db-data &&
cat $BACKUP_DOCKER_PATH/db-data_archive.tar.bz2.parta* > $BACKUP_DOCKER_PATH/db-data_archive.tar.bz2 &&
pwd &&
ls -la &&
docker run --rm -v $VOLUME_NAME:/volume -v $BACKUP_DOCKER_PATH:/backup alpine sh -c "rm -rf /volume/* /volume/..?* /volume/.[!.]* ; tar -C /volume/ -xjf /backup/db-data_archive.tar.bz2"
