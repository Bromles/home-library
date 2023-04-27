BACKUP_PATH=./volume_backup &&
VOLUME_NAME=home-library_db-data &&
cat $BACKUP_PATH:/db-data_archive.tar.bz2.parta* > $BACKUP_PATH:/db-data_archive.tar.bz2 &&
docker run --rm -v $VOLUME_NAME:/volume -v $BACKUP_PATH:/backup alpine sh -c "rm -rf /volume/* /volume/..?* /volume/.[!.]* ; tar -C /volume/ -xjf /backup/db-data_archive.tar.bz2"
