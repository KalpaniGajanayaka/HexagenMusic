package com.example.music_app.model;

    public class MusicModel{

        private int itemId;
        private String collectionID;
        private String itemName;
        private String imageValue;
        private String itemDescirption;

        public String getCollectionID() {
            return collectionID;
        }

        public void setCollectionID(String collectionID) {
            this.collectionID = collectionID;
        }

        public int getItemId() {
            return itemId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getImageValue() {
            return imageValue;
        }

        public void setImageValue(String imageValue) {
            this.imageValue = imageValue;
        }

        public String getItemDescirption() {
            return itemDescirption;
        }

        public void setItemDescirption(String itemDescirption) {
            this.itemDescirption = itemDescirption;
        }
    }
