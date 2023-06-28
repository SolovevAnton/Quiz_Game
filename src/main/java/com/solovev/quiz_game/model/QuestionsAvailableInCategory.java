package com.solovev.quiz_game.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.solovev.quiz_game.model.enums.Difficulty;

import java.util.Map;
import java.util.Objects;

/**
 * Class is used to store json response from API to check the number of available questions in category
 */

public class QuestionsAvailableInCategory {
    @JsonProperty("category_id")
    private Category categoryOnlyId;
    @JsonProperty("category_question_count")
    private CategoryTotalQuestionsCount totalCount;

    /**
     * Method to build map of available questions;
     * @return map of difficulty and corresponding number of available questions got in total count
     */
    public Map<Difficulty,Integer> countQuestionsByDifficulty(){
        return Map.of(
                Difficulty.EASY, totalCount.getTotalEasyQuestionCount(),
                Difficulty.MEDIUM,totalCount.getTotalMediumQuestionCount(),
                Difficulty.HARD,totalCount.getTotalHardQuestionCount()
        );
    }

    /**
     * Subclass to deserialize difficulties
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    class CategoryTotalQuestionsCount {
        @JsonProperty("total_easy_question_count")
        private int totalEasyQuestionCount;
        @JsonProperty("total_medium_question_count")
        private int totalMediumQuestionCount;
        @JsonProperty("total_hard_question_count")
        private int totalHardQuestionCount;

        /**
         * Default for serialization purposes
         */
        public CategoryTotalQuestionsCount() {
        }

        public int getTotalEasyQuestionCount() {
            return totalEasyQuestionCount;
        }

        public void setTotalEasyQuestionCount(int totalEasyQuestionCount) {
            this.totalEasyQuestionCount = totalEasyQuestionCount;
        }

        public int getTotalMediumQuestionCount() {
            return totalMediumQuestionCount;
        }

        public void setTotalMediumQuestionCount(int totalMediumQuestionCount) {
            this.totalMediumQuestionCount = totalMediumQuestionCount;
        }

        public int getTotalHardQuestionCount() {
            return totalHardQuestionCount;
        }

        public void setTotalHardQuestionCount(int totalHardQuestionCount) {
            this.totalHardQuestionCount = totalHardQuestionCount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            CategoryTotalQuestionsCount that = (CategoryTotalQuestionsCount) o;

            if (totalEasyQuestionCount != that.totalEasyQuestionCount) return false;
            if (totalMediumQuestionCount != that.totalMediumQuestionCount) return false;
            return totalHardQuestionCount == that.totalHardQuestionCount;
        }

        @Override
        public int hashCode() {
            int result = totalEasyQuestionCount;
            result = 31 * result + totalMediumQuestionCount;
            result = 31 * result + totalHardQuestionCount;
            return result;
        }

        @Override
        public String toString() {
            return "CategoryTotalQuestionsCount{" +
                    "totalEasyQuestionCount=" + totalEasyQuestionCount +
                    ", totalMediumQuestionCount=" + totalMediumQuestionCount +
                    ", totalHardQuestionCount=" + totalHardQuestionCount +
                    '}';
        }
    }

    public Category getCategoryOnlyId() {
        return categoryOnlyId;
    }

    public void setCategoryOnlyId(Category categoryOnlyId) {
        this.categoryOnlyId = categoryOnlyId;
    }

    public CategoryTotalQuestionsCount getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(CategoryTotalQuestionsCount totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuestionsAvailableInCategory that = (QuestionsAvailableInCategory) o;

        if (!Objects.equals(categoryOnlyId, that.categoryOnlyId))
            return false;
        return Objects.equals(totalCount, that.totalCount);
    }

    @Override
    public int hashCode() {
        int result = categoryOnlyId != null ? categoryOnlyId.hashCode() : 0;
        result = 31 * result + (totalCount != null ? totalCount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "QuestionsInCategory{" +
                "categoryOnlyId=" + categoryOnlyId +
                ", totalCount=" + totalCount +
                '}';
    }
}
