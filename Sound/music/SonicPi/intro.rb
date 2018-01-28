

use_bpm 90

live_loop :riff do
  liste = [:a3, :c4, :d4, :e4]
  
  with_synth :fm do
    3.times do
      play_pattern_timed liste, [0.5], pan:rrand(-1,1)
    end
  end
  with_synth :chipbass  do
    play_pattern_timed liste.reverse, [0.5], amp: 0.6, pan:rrand(-1,1)
  end
end


in_thread do
  sample :vinyl_hiss, amp:0.2
end

live_loop :bassdrum do
  sample :bd_ada, rate:0.9
  sleep 1
end



live_loop :drums do
  sample :drum_heavy_kick
  sleep 1
  sample :drum_snare_hard
  sleep 1
  sample :drum_heavy_kick
  sleep 1
  sample :drum_snare_hard
  sleep 1
end
live_loop :hihat do
  sample :drum_cymbal_closed
  sleep 0.25
  sample :drum_cymbal_pedal
  sleep 1
end


live_loop :bass do
  use_synth :fm
  play :c2
  sleep 0.25
  play :c2
  sleep 2
  play :e2
  sleep 0.75
  play :f2
  sleep 1
end