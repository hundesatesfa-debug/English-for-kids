package com.example

import com.example.data.repository.EducationalContent
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testEducationalContentOromoSupport() {
    assertTrue("Levels should be non-empty", EducationalContent.levels.isNotEmpty())
    val level1 = EducationalContent.levels.first()
    assertEquals("Qubee A hanga Z", level1.oromoTitle)

    assertTrue("Alphabet list should have 26 letters", EducationalContent.alphabetList.size == 26)
    val letterA = EducationalContent.alphabetList.first()
    assertEquals("Aappilii", letterA.oromoWord)
    assertTrue("Letter A has Qubee comparison", letterA.qubeeComparison.contains("Qubee"))

    val shPhonics = EducationalContent.phonicsSounds.first { it.id == "sh" }
    assertTrue("Phonics has Oromo comparison", shPhonics.oromoComparison.contains("Afaan Oromoo"))

    val injeraVocab = EducationalContent.vocabularyList.first { it.id == "oro_7" }
    assertTrue("Injera has Oromo word", injeraVocab.oromoWord.contains("Buddeena"))
  }

  @Test
  fun testLevelProgressionStagesAndDeepExam() {
    val levelJourneys = EducationalContent.levelJourneys
    assertTrue("Should have multiple level journeys", levelJourneys.size >= 4)

    // Test Level 1 Progression
    val part1 = EducationalContent.getLevelJourney(1)
    assertEquals(1, part1.levelNumber)
    assertTrue("Part 1 must start with Letters", part1.targetLetters.isNotEmpty())
    assertTrue("Part 1 must have Phonics sounds", part1.targetPhonics.isNotEmpty())
    assertTrue("Part 1 must have Words", part1.targetWords.isNotEmpty())
    assertTrue("Part 1 must have Sentences", part1.targetSentences.isNotEmpty())
    assertNotNull("Part 1 must have Deep Exam", part1.deepExam)
    assertEquals(75, part1.deepExam.passScore)
    assertTrue("Part 1 Exam must have at least 5 questions", part1.deepExam.questions.size >= 5)

    // Verify Question Stages in Deep Exam
    val examCategories = part1.deepExam.questions.map { it.stageCategory }
    assertTrue(examCategories.contains(com.example.data.model.LevelStage.LETTER))
    assertTrue(examCategories.contains(com.example.data.model.LevelStage.SOUND))
    assertTrue(examCategories.contains(com.example.data.model.LevelStage.WORD))
    assertTrue(examCategories.contains(com.example.data.model.LevelStage.SENTENCE))

    // Test Part 2 (Second Part)
    val part2 = EducationalContent.getLevelJourney(2)
    assertEquals(2, part2.levelNumber)
    assertTrue("Part 2 target letters should cover G-L", part2.targetLetters.any { it.letter == 'G' })
    assertTrue("Part 2 has dental fricative sound", part2.targetPhonics.any { it.id == "th_dental" })
    val thSound = part2.targetPhonics.first { it.id == "th_dental" }
    assertTrue(thSound.pronunciationTip.contains("tongue"))
    assertTrue(thSound.oromoComparison.contains("Afaan Oromoo"))
    assertEquals(75, part2.deepExam.passScore)
  }
}

